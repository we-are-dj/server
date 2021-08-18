package com.dj.server.api.musiclist.service;

import com.dj.server.api.musiclist.dto.request.MusicListDeleteRequestDTO;
import com.dj.server.api.musiclist.dto.request.MusicListModifyRequestDTO;
import com.dj.server.api.musiclist.dto.request.MusicListSaveRequestDTO;
import com.dj.server.api.musiclist.dto.response.MusicAllListResponseDTO;
import com.dj.server.api.musiclist.dto.response.MusicListModifyResponseDTO;
import com.dj.server.api.musiclist.dto.response.MusicListSaveResponseDTO;
import com.dj.server.api.musiclist.entity.MusicList;
import com.dj.server.api.musiclist.repository.MusicListRepository;
import com.dj.server.api.playlist.entity.PlayList;
import com.dj.server.api.playlist.repository.PlayListRepository;
import com.dj.server.common.exception.musicList.MusicListCrudErrorCode;
import com.dj.server.common.exception.musicList.MusicListException;
import com.dj.server.common.exception.playlist.PlayListCrudErrorCode;
import com.dj.server.common.exception.playlist.PlayListException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 음악목록에 대한 전반적인 비즈니스 로직을 담당합니다.
 *
 * @author Informix
 * @created 2021-08-17
 * @since 0.0.1
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MusicListService {

    private final MusicListRepository musicListRepository;
    private final PlayListRepository playListRepository;

    private PlayList fetchPlayList(Long playListId) {
        return playListRepository.findById(playListId)
                .orElseThrow(() -> new PlayListException(PlayListCrudErrorCode.NOT_FOUND));
    }

    /**
     * 특정 재생 목록에 올려둔 모든 음악정보를 가져옵니다.
     *
     * @param playListId 플레이리스트 고유번호
     * @return 플레이리스트 특정 고유아이디에 물려있는 음악리스트 전체
     */
    @Transactional(readOnly = true)
    public List<MusicAllListResponseDTO> fetchAllMusicList(Long playListId) {
        PlayList playList = fetchPlayList(playListId);
        return musicListRepository.findByMusicList(playList.getPlayListId());
    }

    /**
     * 음악 목록을 저장합니다.
     *
     * @param musicListSaveRequestDTO 저장해달라고 요청받은 정보
     * @return 저장한 음악목록 정보
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public MusicListSaveResponseDTO saveMusicList(MusicListSaveRequestDTO musicListSaveRequestDTO) {

        PlayList playList = fetchPlayList(musicListSaveRequestDTO.getPlayListId());
        MusicList musicList = musicListRepository.save(musicListSaveRequestDTO.toEntity(playList));

        return MusicListSaveResponseDTO.builder()
                .musicId(musicList.getMusicId())
                .musicNo(musicList.getMusicNo())
                .musicUrl(musicList.getMusicUrl())
                .build();
    }

    /**
     * 음악 목록 정보 변경
     *
     * @param musicListModifyRequestDTO 변경해달라고 요청받은 값
     * @return 변경된 음악목록 정보
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public MusicListModifyResponseDTO modifyMusicList(MusicListModifyRequestDTO musicListModifyRequestDTO) {

        PlayList playList = fetchPlayList(musicListModifyRequestDTO.getPlayListId());

        MusicList musicList = musicListRepository
                .findByMusicIdAndPlayListId(musicListModifyRequestDTO.getMusicId(), playList)
                .orElseThrow(() -> new MusicListException(MusicListCrudErrorCode.NOT_FOUND));

        if(musicListModifyRequestDTO.getMusicNo() != null) {
            final Integer musicNo = musicListModifyRequestDTO.getMusicNo();
            musicListRepository.findByMusicIdAndMusicNo(musicList, musicNo).map((e) -> musicList.updateMusicNo(musicNo));
        }

        if(musicListModifyRequestDTO.getMusicUrl() != null) {
            final String musicUrl = musicListModifyRequestDTO.getMusicUrl();
            musicListRepository.findByMusicIdAndMusicUrl(musicList, musicUrl).map((e) -> musicList.updateMusicUrl(musicUrl));
        }

        musicList.updateMusicList(musicListModifyRequestDTO);

        return MusicListModifyResponseDTO.builder()
                .musicId(musicList.getMusicId())
                .musicNo(musicList.getMusicNo())
                .musicUrl(musicList.getMusicUrl())
                .build();
    }

    /**
     * 음악 목록 삭제.
     *
     * @param musicListDeleteRequestDTO 삭제해달라고 요청받은 값
     * @return 삭제 처리 메시지
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public String deleteMusicList(MusicListDeleteRequestDTO musicListDeleteRequestDTO) {

        PlayList playList = fetchPlayList(musicListDeleteRequestDTO.getPlayListId());
        MusicList musicList = musicListRepository
                .findByMusicIdAndPlayListId(musicListDeleteRequestDTO.getMusicId(), playList)
                .orElseThrow(() -> new MusicListException(MusicListCrudErrorCode.NOT_FOUND));

        musicListRepository.delete(musicList);

        return "음악목록이 삭제되었습니다.";
    }
}
