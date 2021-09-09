package com.dj.server.api.room.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@NoArgsConstructor
public class MusicRoomSearchRequestDTO {

    private String roomName;

    @Value("1") // default 1
    private Integer pageNo;

}
