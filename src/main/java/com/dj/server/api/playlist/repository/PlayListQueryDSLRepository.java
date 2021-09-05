package com.dj.server.api.playlist.repository;

import com.dj.server.api.playlist.model.dto.response.PlayAllListResponseDTO;

import java.util.List;

public interface PlayListQueryDSLRepository {

    List<PlayAllListResponseDTO> findByMemberAllPlayList(Long member);


}
