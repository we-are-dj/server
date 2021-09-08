package com.dj.server.api.musiclist.repository;

import com.dj.server.api.musiclist.dto.response.MusicAllListResponseDTO;
import java.util.List;

public interface MusicListQueryDSLRepository {
    List<MusicAllListResponseDTO> findByMusicList(Long playListId);

    Integer findByPlayListLastMusicNo(Long playListId);

}
