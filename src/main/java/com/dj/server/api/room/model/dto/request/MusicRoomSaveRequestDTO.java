package com.dj.server.api.room.model.dto.request;


import com.dj.server.api.member.entity.Member;
import com.dj.server.api.room.entity.MusicRoom;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MusicRoomSaveRequestDTO {

    private final String roomName;

    public MusicRoom toEntity(Member member) {
        return MusicRoom.builder()
                .roomName(roomName)
                .roomMaster(member)
                .build();
    }

}
