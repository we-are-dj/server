package com.dj.server.api.room.service;

import com.dj.server.api.room.model.dto.request.ChatMessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final SimpMessageSendingOperations messageSendingOperations;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            // redis에서 발행된 데이터를 받아 deserialize
            String publishMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());
            // ChatMessage 객채로 맵핑
            ChatMessageDTO roomMessage = objectMapper.readValue(publishMessage, ChatMessageDTO.class);
            // Websocket 구독자에게 채팅 메시지 Send
            messageSendingOperations.convertAndSend("/sub/chat/room/" + roomMessage.getRoomId(), roomMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
