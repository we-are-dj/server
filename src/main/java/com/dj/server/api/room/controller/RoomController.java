package com.dj.server.api.room.controller;


import com.dj.server.api.room.model.dto.request.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class RoomController {


    private final SimpMessageSendingOperations messageSendingOperations;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public ChatMessageDTO message(ChatMessageDTO messageDTO, @Header("access_token") String header12) {
        if (ChatMessageDTO.MessageType.JOIN.equals(messageDTO.getType())) {
            messageDTO.setMessage(messageDTO.getSender() + " 님이 입장하셨습니다.");
        }

        log.info(header12);
        
        messageDTO.setMessage("통신완료");
        
//        messageSendingOperations.convertAndSend("/sub/chat/room/" + messageDTO.getRoomId(), messageDTO);
        return messageDTO;
    }

}
