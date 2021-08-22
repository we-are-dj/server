package com.dj.server.api.websocket.controller;

import com.dj.server.api.websocket.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 실시간 채팅 컨트롤러
 *
 * @author Informix
 * @created 2021-08-23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final SimpMessagingTemplate webSocket;

    /**
     * MessageMapping: client webSocket에서 "sendMessage" 함수를 사용하여 메시지를 보냈을 경우
     *                 서버에서 그 이벤트를 트리거하는 애너테이션
     *
     * SendTo: 해당 topics를 수신하는 Client webSocket에 메시지를 전달합니다. SendTo 애너테이션을 사용할 경우
     *         해당 메서드는 반드시 리턴 타입이 있어야 합니다.
     *         비교: @see SendTemplateMessage method
     *
     * @param chatMessage 유저가 보낸 채팅 메시지
     */
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage SendToMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    /**
     * SimpMessagingTemplate을 사용하면 SendTo 어노테이션을 사용하지 않고 응답 메시지를 보낼 수 있습니다.
     * 이때 반환값은 없어야 합니다. (반드시 void 타입의 메서드여야 합니다.)
     * convertAndSend 메서드는 특정 유저에게만 메시지를 보낼 수 있는 기능입니다.
     */
    @MessageMapping("/template")
    public void SendTemplateMessage() {
        webSocket.convertAndSend("/topics/template", "Template");
    }

    /**
     * 웹소켓과는 상관없이, 외부의 GET 질의에 대한 이벤트를 트리거할 수 있습니다.
     */
    @GetMapping(value = "/api")
    public void SendAPI() {
        webSocket.convertAndSend("/topics/api", "WE-ARE-DJ WEBSOCKET CHAT API");
    }

    /**
     * @param chatMessage 유저가 보낸 채팅 메시지
     * @param headerAccessor 헤더 매핑
     * @return 채팅 메시지
     */
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", chatMessage.getSender());
        return chatMessage;
    }
}
