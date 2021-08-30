package com.dj.server.api.room.controller;


import com.dj.server.api.room.model.dto.request.ChatMessageDTO;
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

    /**
     * 구독자들에게 메세지를 보내줄 도우미
     */
    private final SimpMessageSendingOperations messageSendingOperations;

    @MessageMapping("/chat/message")
    public void message(ChatMessageDTO messageDTO, @Header(value = "access_token" , required = false) String header12) {
        if (ChatMessageDTO.MessageType.JOIN.equals(messageDTO.getType())) {
            messageDTO.setMessage(messageDTO.getSender() + " 님이 입장하셨습니다.");
        }
        
        messageSendingOperations.convertAndSend("/sub/room/" + messageDTO.getRoomId() , messageDTO);
    }


}
