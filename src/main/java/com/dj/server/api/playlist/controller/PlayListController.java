package com.dj.server.api.playlist.controller;

import com.dj.server.api.common.response.ResponseDTO;
import com.dj.server.api.playlist.model.dto.request.PlayListModifyRequestDTO;
import com.dj.server.api.playlist.model.dto.request.PlayListSaveRequestDTO;
import com.dj.server.api.playlist.model.dto.response.PlayAllListResponseDTO;
import com.dj.server.api.playlist.model.dto.response.PlayListModifyResponseDTO;
import com.dj.server.api.playlist.model.dto.response.PlayListSaveResponseDTO;
import com.dj.server.api.playlist.service.PlayListService;
import com.dj.server.common.jwt.JwtUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * 회원의 재생목록 도메인을 관리하는 컨트롤러 입니다.
 *
 * @author JaeHyun
 * @created 2021-08-15
 * @since 0.0.1
 *
 */

@Slf4j
@RequestMapping("/v1")
@RequiredArgsConstructor
@RestController
public class PlayListController {

    private final PlayListService playListService;
    private final JwtUtil jwtUtil;


    @ApiOperation(value = "fetchMemberAllPlayList",
            notes = "회원의 모든 재생목록을 조회합니다. 회원의 액세스 토큰을 헤더에 담아 전송해주세요")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping("/playList")
    public ResponseDTO<List<PlayAllListResponseDTO>> fetchMemberAllPlayList() {
        return new ResponseDTO<>(playListService.fetchMemberAllPlayList(jwtUtil.getMemberId()), "SUCCESS", HttpStatus.OK);
    }

    @ApiOperation(value = "playListSave",
            notes = "회원의 재생목록을 생성합니다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @PostMapping("/playList")
    public ResponseDTO<PlayListSaveResponseDTO> playListSave(PlayListSaveRequestDTO playListSaveRequestDTO) {
        return new ResponseDTO<>(playListService.playListSave(jwtUtil.getMemberId(), playListSaveRequestDTO), "SUCCESS", HttpStatus.OK);
    }

    @ApiOperation(value = "modifyPlayList",
            notes = "회원의 재생목록을 업데이트 합니다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @PatchMapping("/playList")
    public ResponseDTO<PlayListModifyResponseDTO> modifyPlayList(PlayListModifyRequestDTO playListModifyRequestDTO) {
        return new ResponseDTO<>(playListService.modifyPlayList(jwtUtil.getMemberId(), playListModifyRequestDTO), "SUCCESS", HttpStatus.OK);
    }




}
