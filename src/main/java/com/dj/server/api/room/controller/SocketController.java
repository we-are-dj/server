package com.dj.server.api.room.controller;


import com.dj.server.api.room.model.dto.request.ChatMessageDTO;
import com.dj.server.api.room.service.RedisPublisher;
import com.dj.server.api.room.service.MusicRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

/**
 *
 * 소켓 관련 컨트롤러 ( 여러 컨트롤러로 분리 해도 됩니다.)
 *
 */

@Slf4j
@RequiredArgsConstructor
@Controller
public class SocketController {

    private final RedisPublisher redisPublisher;
    private final MusicRoomService musicRoomService;

    /**
     * 구독자들에게 메세지를 보내줄 도우미
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessageDTO messageDTO) {
        if (ChatMessageDTO.MessageType.JOIN.equals(messageDTO.getType())) {
            musicRoomService.enterChatRoom(messageDTO.getRoomId());
            messageDTO.setMessage(messageDTO.getSender() + " 님이 입장하셨습니다.");
        }

        redisPublisher.publish(musicRoomService.getTopic(messageDTO.getRoomId()), messageDTO);
    }


}
