package com.dj.server.api.room.controller;

import com.dj.server.api.room.model.dto.request.ChatRoomDTO;
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


    @GetMapping("/rooms")
    public List<ChatRoomDTO> findByAllRoom() {
        return roomService.findAllRoom();
    }

    @PostMapping("/room")
    public ChatRoomDTO createRoom(@RequestParam String name) {
        return roomService.createChatRoom(name);
    }

    @GetMapping("/room/{roomId}")
    public ChatRoomDTO roomInfo(@PathVariable String roomId) {
        return roomService.findByRoomId(roomId);
    }


}
