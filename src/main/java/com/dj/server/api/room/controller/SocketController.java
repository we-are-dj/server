package com.dj.server.api.room.controller;


import com.dj.server.api.room.model.dto.request.ChatMessageDTO;
import com.dj.server.api.room.service.RedisPublisher;
import com.dj.server.api.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
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
    private final RoomService roomService;

    /**
     * 구독자들에게 메세지를 보내줄 도우미
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessageDTO messageDTO, @Header(value = "access_token" , required = false) String header12) {
        if (ChatMessageDTO.MessageType.JOIN.equals(messageDTO.getType())) {
            roomService.enterChatRoom(messageDTO.getRoomId());
            messageDTO.setMessage(messageDTO.getSender() + " 님이 입장하셨습니다.");
        }

        redisPublisher.publish(roomService.getTopic(messageDTO.getRoomId()), messageDTO);
    }


}
