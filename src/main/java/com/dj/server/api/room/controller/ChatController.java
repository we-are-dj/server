package com.dj.server.api.room.controller;

import com.dj.server.api.room.model.dto.request.ChatRoomRequestDTO;
import com.dj.server.api.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final RoomService roomService;

    @PostMapping
    public ChatRoomRequestDTO createRoom(@RequestParam String name) {
        return roomService.createRoom(name);
    }

    @GetMapping
    public List<ChatRoomRequestDTO> findAllRoom() {
        return roomService.findAllRoom();
    }

}
