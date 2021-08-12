package com.dj.server.api.playlist.service;

import com.dj.server.api.member.repository.MemberRepository;
import com.dj.server.api.playlist.model.dto.response.MemberPlayListResponseDTO;
import com.dj.server.api.playlist.repository.PlayListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 재생목록 대한 전반적인 비즈니스 로직을 담당합니다.
 *
 * @author JaeHyun
 * @created 2021-08-12
 * @since 0.0.1
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class PlayListService {

    private final PlayListRepository playListRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<MemberPlayListResponseDTO> fetchMemberPlayList() {
        return null;
    }

}
