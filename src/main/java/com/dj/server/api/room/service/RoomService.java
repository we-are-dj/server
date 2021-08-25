package com.dj.server.api.room.service;


import com.dj.server.api.room.model.dto.request.ChatMessageRequestDTO;
import com.dj.server.api.room.model.dto.request.ChatRoomRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final ObjectMapper objectMapper;
    private Map<String , ChatRoomRequestDTO> chatRoomRequestDTOMap;

    @PostConstruct
    private void init() {
        chatRoomRequestDTOMap = new LinkedHashMap<>();
    }

    public List<ChatRoomRequestDTO> findAllRoom() {
        return new ArrayList<>(chatRoomRequestDTOMap.values());
    }

    public ChatRoomRequestDTO findRoomById(String roomId) {
        return chatRoomRequestDTOMap.get(roomId);
    }

    public ChatRoomRequestDTO createRoom(String name) {
        String randomId = UUID.randomUUID().toString();
        ChatRoomRequestDTO chatRoomRequestDTO = ChatRoomRequestDTO.builder()
                .roomId(randomId)
                .name(name)
                .build();
        chatRoomRequestDTOMap.put(randomId, chatRoomRequestDTO);
        return chatRoomRequestDTO;
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}
