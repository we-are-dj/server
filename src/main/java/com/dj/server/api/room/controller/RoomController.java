package com.dj.server.api.room.controller;

import com.dj.server.api.common.response.ResponseDTO;
import com.dj.server.api.room.model.dto.request.MusicRoomSearchRequestDTO;
import com.dj.server.api.room.model.dto.response.MusicRoomJoinResponseDTO;
import com.dj.server.api.room.model.dto.response.MusicRoomSaveResponseDTO;
import com.dj.server.api.room.model.dto.request.MusicRoomSaveRequestDTO;
import com.dj.server.api.room.model.dto.response.MusicRoomSearchResponseDTO;
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
 * 음악방 관련 API 컨트롤러
 *
 */

@RequiredArgsConstructor
@RequestMapping("/v1/music/")
@RestController
public class RoomController {

    private final MusicRoomService musicRoomService;
    private final JwtUtil jwtUtil;

    @ApiOperation(value = "SearchMusicRoom", notes = "MusicRoom 검색")
    @ApiResponses({
            @ApiResponse(code = 200, message = "SUCCESS")
    })
    @GetMapping("/rooms")
    public ResponseDTO<List<MusicRoomSearchResponseDTO>> findByAllRoom(MusicRoomSearchRequestDTO musicRoomSearchRequestDTO) {
        return new ResponseDTO<>(musicRoomService.findAllRoom(musicRoomSearchRequestDTO), "SUCCESS", HttpStatus.OK);
    }


    @ApiOperation(value = "CreateMusicRoom", notes = "MusicRoom 생성")
    @ApiResponses({
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 422, message = "생성할수 있는 MusicRoom 을 초과 하였습니다.")
    })
    @PostMapping("/room")
    public ResponseDTO<MusicRoomSaveResponseDTO> createRoom(MusicRoomSaveRequestDTO musicRoomSaveRequestDTO) {
        return new ResponseDTO<>(musicRoomService.createMusicRoom(jwtUtil.getMemberId() ,musicRoomSaveRequestDTO), "SUCCESS", HttpStatus.OK);
    }

    @ApiOperation(value = "join room" , notes = "방 입장")
    @ApiResponses({
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 400, message = "해당 방이 존재하지 않습니다.")
    })
    @GetMapping("/room/{roomId}")
    public ResponseDTO<MusicRoomJoinResponseDTO> joinMusicRoom(@PathVariable Long roomId) {
        return new ResponseDTO<>(musicRoomService.joinMusicRoom(roomId), "SUCCESS", HttpStatus.OK);
    }


}
