package com.dj.server.api.playlist.controller;

import com.dj.server.api.common.response.ResponseDTO;
import com.dj.server.api.playlist.model.dto.response.MemberPlayListResponseDTO;
import com.dj.server.api.playlist.repository.PlayListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * 회원의 재생목록 도메인을 관리하는 컨트롤러 입니다.
 *
 * @author JaeHyun
 * @create 2021-08-15
 * @since 0.0.1
 *
 */

@Slf4j
@RequestMapping("/v1")
@RequiredArgsConstructor
@RestController
public class PlayListController {

    private final PlayListRepository playListRepository;

    @GetMapping("/playList")
    public ResponseDTO<List<MemberPlayListResponseDTO>> fetchMemberPlayList() {
        return null;
    }

}
