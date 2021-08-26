package com.dj.server.api.room.controller;

import com.dj.server.api.room.model.dto.request.ChatRoomRequestDTO;
import com.dj.server.api.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * 웹소켓 정보를 얻는 컨트롤러
 * 프로토콜 : HTTP
 *
 */

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
