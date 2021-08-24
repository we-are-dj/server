package com.dj.server.api.websocket.service;

import com.dj.server.api.websocket.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 채팅방 입장 및 퇴장을 처리합니다.
 *
 * @author Informix
 * @created 2021-08-23
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private ChannelTopic channelTopic;
    private final RedisTemplate<?, ?> redisTemplate;
    private final ChatRoomService chatRoomService;
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);

    /**
     * destination 정보에서 roomId 추출
     */
    public String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1)
            return destination.substring(lastIndex + 1);
        else
            return "";
    }

    /**
     * 채팅방에 메시지 발송
     */
    public void sendChatMessage(ChatMessage chatMessage) {
        log.info("메세지 발송 단계 진입");

        long systemTime = System.currentTimeMillis();
        String now = formatter.format(systemTime);
        chatMessage.setTimenow(now);
        chatMessage.setUserCount(chatRoomService.getUserCount(chatMessage.getRoomId()));

        if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
            chatMessage.setMessage(chatMessage.getMemberNickName() + "님이 방에 입장했습니다.");
            chatMessage.setMemberNickName("[알림]");

        } else if (ChatMessage.MessageType.QUIT.equals(chatMessage.getType())) {
            chatMessage.setMessage(chatMessage.getMemberNickName() + "님이 방에서 나갔습니다.");
            chatMessage.setMemberNickName("[알림]");
        }

        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
    }

}
