package com.dj.server.api.room.controller;


import com.dj.server.api.room.model.dto.request.ChatMessageRequestDTO;
import com.dj.server.api.room.model.dto.request.ChatRoomRequestDTO;
import com.dj.server.api.room.service.RoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class RoomController extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final RoomService roomService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String payload = message.getPayload();
        log.info("payload {}", payload);
//        TextMessage textMessage = new TextMessage("hi");
//        session.sendMessage(textMessage);

        ChatMessageRequestDTO chatMessageRequestDTO = objectMapper.readValue(payload, ChatMessageRequestDTO.class);
        ChatRoomRequestDTO roomRequestDTO = roomService.findRoomById(chatMessageRequestDTO.getRoomId());
        roomRequestDTO.handleActions(session, chatMessageRequestDTO,  roomService);

    }
}
