package com.dj.server.api.playlist.service;

import com.dj.server.api.member.entity.Member;
import com.dj.server.api.member.repository.MemberRepository;
import com.dj.server.api.playlist.entity.PlayList;
import com.dj.server.api.playlist.model.dto.request.PlayListDeleteRequestDTO;
import com.dj.server.api.playlist.model.dto.request.PlayListModifyRequestDTO;
import com.dj.server.api.playlist.model.dto.request.PlayListSaveRequestDTO;
import com.dj.server.api.playlist.model.dto.response.PlayAllListResponseDTO;
import com.dj.server.api.playlist.model.dto.response.PlayListModifyResponseDTO;
import com.dj.server.api.playlist.model.dto.response.PlayListSaveResponseDTO;
import com.dj.server.api.playlist.repository.PlayListRepository;
import com.dj.server.common.exception.member.MemberCrudErrorCode;
import com.dj.server.common.exception.member.MemberException;
import com.dj.server.common.exception.playlist.PlayListCrudErrorCode;
import com.dj.server.common.exception.playlist.PlayListException;
import com.dj.server.common.jwt.JwtUtil;
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
    private final JwtUtil jwtUtil;


    //회원을 자주 조회할거 같아서 공통 메소드로 추가
    private Member fetchMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new MemberException(MemberCrudErrorCode.NOT_FOUND_MEMBER));
    }

    /**
     *
     * 회원의 재생목록을 조회합니다.
     *
     * @param memberId
     * @return
     */
    @Transactional(readOnly = true)
    public List<PlayAllListResponseDTO> fetchMemberAllPlayList(Long memberId) {

        //토큰에 있는 회원 ID로 유저 조회
        Member member = fetchMember(memberId);

//        List<PlayList> playList = playListRepository.findByMember(member);
//
//        List<MemberPlayListResponseDTO> playListResponseDTOList = new ArrayList<>();
//
//        for(PlayList play : playList) {
//            playListResponseDTOList.add(MemberPlayListResponseDTO.builder()
//                    .playListName(play.getPlayListName())
//                    .use(play.getUse()).build());
//        }

        return playListRepository.findByMemberAllPlayList(member.getMemberId());
    }

    /**
     *
     * 재생목록을 저장합니다.
     *
     * @param playListSaveRequestDTO
     * @param memberId
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public PlayListSaveResponseDTO playListSave(Long memberId, PlayListSaveRequestDTO playListSaveRequestDTO) {

        //회원 조회
        Member member = fetchMember(memberId);

        //저장
        PlayList playList = playListRepository.save(playListSaveRequestDTO.toEntity(member));

        //DTO 로 변환

        return PlayListSaveResponseDTO.builder()
                .playListId(playList.getPlayListId())
                .playListName(playList.getPlayListName())
                .use(playList.getUse()).build();
    }

    /**
     *
     * 회원의 재생목록의 내부 값들을 변경합니다.
     *
     * @param memberId
     * @param playListModifyRequestDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public PlayListModifyResponseDTO modifyPlayList(Long memberId, PlayListModifyRequestDTO playListModifyRequestDTO) {

        Member member = fetchMember(memberId);

        //PlayList Entity 를 가져옵니다.
        PlayList playList = playListRepository.findByPlayListIdAndMember(playListModifyRequestDTO.getPlayListId(), member).orElseThrow(() -> new PlayListException(PlayListCrudErrorCode.NOT_FOUND));


        //변경 하려는 값이  사용값인지 확인
        if(playListModifyRequestDTO.getUse() != null) {
            final String use = "Y";
            playListRepository.findByMemberAndUse(member, use).map(PlayList::updateUseNo);
        }

        //업데이트
        playList.updatePlayList(playListModifyRequestDTO);

        //DTO 로 변환합니다.
        return PlayListModifyResponseDTO.builder()
                .playListId(playList.getPlayListId())
                .playListName(playList.getPlayListName())
                .use(playList.getUse())
                .build();
    }

    /**
     *
     * 재생목록을 상제합니다.
     *
     * @param memberId
     * @param playListDeleteRequestDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public String deletePlayList(Long memberId, PlayListDeleteRequestDTO playListDeleteRequestDTO) {

        //회원 정보를 조회합니다.
        Member member = fetchMember(memberId);

        //PlayList Entity 를 가져옵니다.
        PlayList playList = playListRepository.findByPlayListIdAndMember(playListDeleteRequestDTO.getPlayListId(), member).orElseThrow(() -> new PlayListException(PlayListCrudErrorCode.NOT_FOUND));

        playListRepository.delete(playList);

        return "SUCCESS";
    }







}
