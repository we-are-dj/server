package com.dj.server.api.room.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class MusicRoomJoinResponseDTO {

    private final Long roomId;
    private final String roomName;

    @Builder
    public MusicRoomJoinResponseDTO(Long roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }
}
