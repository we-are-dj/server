package com.dj.server.api.room.controller;

import com.dj.server.api.room.model.dto.request.ChatRoomDTO;
import com.dj.server.api.room.model.dto.request.MusicRoomSaveRequestDTO;
import com.dj.server.api.room.service.MusicRoomService;
import com.dj.server.common.jwt.JwtUtil;
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
@RequestMapping("/v1")
@RestController
public class RoomController {

    private final MusicRoomService musicRoomService;
    private final JwtUtil jwtUtil;


    @GetMapping("/rooms")
    public List<ChatRoomDTO> findByAllRoom() {
        return musicRoomService.findAllRoom();
    }

    @PostMapping("/room")
    public ChatRoomDTO createRoom(MusicRoomSaveRequestDTO musicRoomSaveRequestDTO) {
        return musicRoomService.createChatRoom(jwtUtil.getMemberId() ,musicRoomSaveRequestDTO);
    }

    @GetMapping("/room/{roomId}")
    public ChatRoomDTO roomInfo(@PathVariable String roomId) {
        return musicRoomService.findByRoomId(roomId);
    }


}
