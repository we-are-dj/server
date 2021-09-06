package com.dj.server.api.room.model.dto.request;


import com.dj.server.api.member.entity.Member;
import com.dj.server.api.room.entity.MusicRoom;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class MusicRoomSaveRequestDTO {

    @ApiModelProperty(required = true, value = "방 이름", example = "POP 같이 들어요")
    @NotNull
    private final String roomName;

    public MusicRoom toEntity(Member member) {
        return MusicRoom.builder()
                .roomName(roomName)
                .roomMaster(member)
                .build();
    }

}
