package com.dj.server.api.room.repository.room;

import com.dj.server.api.room.model.dto.request.MusicRoomSearchRequestDTO;
import com.dj.server.api.room.model.dto.response.MusicRoomSearchResponseDTO;

import java.util.List;

public interface MusicRoomQueryDSLRepository {

    List<MusicRoomSearchResponseDTO> findByMusicRoomSearchList(MusicRoomSearchRequestDTO musicRoomSearchRequestDTO);

}
