package com.dj.server.api.musiclist.controller;

import com.dj.server.api.common.response.ResponseDTO;
import com.dj.server.api.musiclist.dto.request.MusicListModifyRequestDTO;
import com.dj.server.api.musiclist.dto.request.MusicListSaveRequestDTO;
import com.dj.server.api.musiclist.dto.response.MusicAllListResponseDTO;
import com.dj.server.api.musiclist.dto.response.MusicListModifyResponseDTO;
import com.dj.server.api.musiclist.dto.response.MusicListSaveResponseDTO;
import com.dj.server.api.musiclist.service.MusicListService;
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
 * 회원의 재생목록 도메인을 관리하는 컨트롤러 입니다.
 *
 * @author Informix
 * @created 2021-08-17
 * @since 0.0.1
 *
 */
@Slf4j
@RequestMapping("/v1")
@RequiredArgsConstructor
@RestController
public class MusicListController {

    private final MusicListService playListService;
    private final JwtUtil jwtUtil;

    @ApiOperation(value = "fetchAllMusicList",
            notes = "회원의 모든 재생목록을 조회합니다 토큰만 전송해주세요")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping("/musicList")
    public ResponseDTO<List<MusicAllListResponseDTO>> fetchMemberAllPlayList() {
        return new ResponseDTO<>(playListService.fetchAllMusicList(jwtUtil.getMemberId()), "SUCCESS", HttpStatus.OK);
    }

    @ApiOperation(value = "saveMusicList",
            notes = "재생목록을 생성합니다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @PostMapping("/musicList")
    public ResponseDTO<MusicListSaveResponseDTO> playListSave(MusicListSaveRequestDTO musicListSaveRequestDTO) {
        return new ResponseDTO<>(playListService.playListSave(jwtUtil.getMemberId(), musicListSaveRequestDTO), "SUCCESS", HttpStatus.OK);
    }

    @ApiOperation(value = "modifyMusicList",
            notes = "재생목록을 업데이트 합니다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @PatchMapping("/musicList")
    public ResponseDTO<MusicListModifyResponseDTO> modifyPlayList(MusicListModifyRequestDTO musicListModifyRequestDTO) {
        return new ResponseDTO<>(playListService.modifyMusicList(jwtUtil.getMemberId(), musicListModifyRequestDTO), "SUCCESS", HttpStatus.OK);
    }




}
