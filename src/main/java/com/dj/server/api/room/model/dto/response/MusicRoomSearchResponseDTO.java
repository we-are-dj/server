package com.dj.server.api.room.model.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class MusicRoomSearchResponseDTO {

    private final Long roomId;

    //방 번호 검색 순서
    private Long musicRoomNo;

    private final String roomName;

    private final Integer roomUserCount;

}
