package com.dj.server.api.musiclist.controller;

import com.dj.server.api.common.response.ResponseDTO;
import com.dj.server.api.musiclist.dto.request.MusicListDeleteRequestDTO;
import com.dj.server.api.musiclist.dto.request.MusicListModifyRequestDTO;
import com.dj.server.api.musiclist.dto.request.MusicListSaveRequestDTO;
import com.dj.server.api.musiclist.dto.response.MusicAllListResponseDTO;
import com.dj.server.api.musiclist.dto.response.MusicListModifyResponseDTO;
import com.dj.server.api.musiclist.dto.response.MusicListSaveResponseDTO;
import com.dj.server.api.musiclist.service.MusicListService;
import com.dj.server.common.exception.musicList.handler.InvalidModifyMusicListParameterException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 회원의 재생목록 도메인을 관리하는 컨트롤러 입니다.
 *
 * @author Informix
 * @created 2021-08-17
 * @since 0.0.1
 */
@Slf4j
@RequestMapping("/v1")
@RequiredArgsConstructor
@RestController
public class MusicListController {

    private final MusicListService musicListService;

    @ApiOperation(value = "fetchAllMusicList",
            notes = "재생목록에 포함된 전체 음악목록을 반환합니다. 재생목록 번호를 보내주세요.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping("/musicList")
    public ResponseDTO<List<MusicAllListResponseDTO>> fetchAllMusicList(@RequestParam("playListId") Long playListId) {
        return new ResponseDTO<>(musicListService.fetchAllMusicList(playListId), "SUCCESS", HttpStatus.OK);
    }

    @ApiOperation(value = "saveMusicList",
            notes = "음악목록을 생성합니다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @PostMapping("/musicList")
    public ResponseDTO<MusicListSaveResponseDTO> saveMusicList(MusicListSaveRequestDTO musicListSaveRequestDTO) {
        return new ResponseDTO<>(musicListService.saveMusicList(musicListSaveRequestDTO), "SUCCESS", HttpStatus.OK);
    }

    @ApiOperation(value = "modifyMusicListPlayOrder",
            notes = "음악목록 순서를 업데이트합니다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @PatchMapping("/musicList")
    public ResponseDTO<MusicListModifyResponseDTO> modifyMusicListPlayOrder(MusicListModifyRequestDTO musicListModifyRequestDTO) {
        return new ResponseDTO<>(musicListService.modifyMusicListPlayOrder(musicListModifyRequestDTO), "SUCCESS", HttpStatus.OK);
    }

    @ApiOperation(value = "deleteMusicList",
            notes = "음악목록을 삭제합니다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @DeleteMapping("/musicList")
    public ResponseDTO<String> deleteMusicList(MusicListDeleteRequestDTO musicListDeleteRequestDTO) {
        return new ResponseDTO<>(musicListService.deleteMusicList(musicListDeleteRequestDTO), "SUCCESS", HttpStatus.OK);
    }

}
