package com.dj.server.api.room.controller;

import com.dj.server.api.common.response.ResponseDTO;
import com.dj.server.api.room.model.dto.response.MusicRoomSaveResponseDTO;
import com.dj.server.api.room.model.dto.request.MusicRoomSaveRequestDTO;
import com.dj.server.api.room.service.MusicRoomService;
import com.dj.server.common.jwt.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public List<MusicRoomSaveResponseDTO> findByAllRoom() {
        return musicRoomService.findAllRoom();
    }


    @ApiOperation(value = "CreateMusicRoom", notes = "MusicRoom 생성")
    @ApiResponses({
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 422, message = "생성할수 있는 MusicRoom 을 초과 하였습니다.")
    })
    @PostMapping("/room")
    public ResponseDTO<MusicRoomSaveResponseDTO> createRoom(MusicRoomSaveRequestDTO musicRoomSaveRequestDTO) {
        return new ResponseDTO<>(musicRoomService.createChatRoom(jwtUtil.getMemberId() ,musicRoomSaveRequestDTO), "SUCCESS", HttpStatus.OK);
    }


    @GetMapping("/room/{roomId}")
    public MusicRoomSaveResponseDTO roomInfo(@PathVariable String roomId) {
        return musicRoomService.findByRoomId(roomId);
    }


}
