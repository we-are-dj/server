package com.dj.server.api.musiclist.repository;

import com.dj.server.api.musiclist.dto.response.MusicAllListResponseDTO;
import com.dj.server.api.musiclist.entity.MusicList;
import com.dj.server.api.playlist.entity.PlayList;

import java.util.List;
import java.util.Optional;

public interface MusicListQueryDSLRepository {
    List<MusicAllListResponseDTO> findByMusicList(Long playListId);
    Optional<MusicList> findByMusicIdAndPlayListId(Long musicId, PlayList playList);
}
