package com.dj.server.api.room.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDTO {

    public enum MessageType {
        ENTER, TALK, JOIN, QUIT
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
    private long userCount;

}
