package com.dj.server.api.websocket.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomDTO {
    private String roomName;
    private String userInterested;
}