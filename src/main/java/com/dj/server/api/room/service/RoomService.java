package com.dj.server.api.room.service;


import com.dj.server.api.room.model.dto.request.ChatRoomDTO;
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

    private Map<String , ChatRoomDTO> chatRoomDTOMap;

    @PostConstruct
    private void init() {
        chatRoomDTOMap = new LinkedHashMap<>();
    }

    public List<ChatRoomDTO> findAllRoom() {

        List<ChatRoomDTO> chatRoomList = new ArrayList<>(chatRoomDTOMap.values());
        Collections.reverse(chatRoomList);
        return chatRoomList;
    }

    public ChatRoomDTO findByRoomId(String id) {
        return chatRoomDTOMap.get(id);
    }

    public ChatRoomDTO createChatRoom(String name) {
        ChatRoomDTO chatRoomDTO = ChatRoomDTO.create(name);
        chatRoomDTOMap.put(chatRoomDTO.getRoomId(), chatRoomDTO);
        return chatRoomDTO;
    }


}
