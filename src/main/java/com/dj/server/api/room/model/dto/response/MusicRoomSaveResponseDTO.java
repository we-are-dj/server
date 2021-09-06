package com.dj.server.api.room.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 *
 * pub / sub 방식을 구현합니다
 * pub / sub 방식을 이용하면 구독자 관리가 알아서 되므로
 * 웹소켓 세션 관리가 필요 없어 집니다.
 * 또한 발송의 구현도 알아서 해결되므로 일일이 클라이언트에게 메시지를 발송하지 않아도 됩니다.
 *
 */

@Getter
public class MusicRoomSaveResponseDTO implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    private final Long roomId;
    private final String roomName;

    @Builder
    public MusicRoomSaveResponseDTO(Long roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }
}
