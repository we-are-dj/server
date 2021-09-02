package com.dj.server.api.room.service;


import com.dj.server.api.room.model.dto.request.ChatRoomDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoomService {

    // 채팅방에 발행되는 메시지를 처리할 Listner
    private final RedisMessageListenerContainer redisMessageListenerContainer;

    //구독 처리
    private final RedisSubscriber redisSubscriber;

    //Redis
    private static final String CHAT_ROOMS = "CHAT_ROOM";
    private final RedisTemplate<String , Object> redisTemplate;
    private HashOperations<String, String , ChatRoomDTO> operations;

    private Map<String, ChannelTopic> topics;


    @PostConstruct
    private void init() {
        operations = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }

    public List<ChatRoomDTO> findAllRoom() {
        return operations.values(CHAT_ROOMS);
    }

    public ChatRoomDTO findByRoomId(String id) {
        return operations.get(CHAT_ROOMS, id);
    }

    /**
     *
     * 채팅방 생성 : 서버간 채팅방 공유를 위해 redis Hash에 저장
     *
     * @param name
     * @return
     */
    public ChatRoomDTO createChatRoom(String name) {
        ChatRoomDTO chatRoomDTO = ChatRoomDTO.create(name);
        operations.put(CHAT_ROOMS, chatRoomDTO.getRoomId(), chatRoomDTO);
        return chatRoomDTO;
    }

    public void enterChatRoom(String roomId) {
        ChannelTopic topic = topics.get(roomId);
        if(topic == null) {
            topic = new ChannelTopic(roomId);
            redisMessageListenerContainer.addMessageListener(redisSubscriber,topic);
            topics.put(roomId, topic);
        }
    }

    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }


}
