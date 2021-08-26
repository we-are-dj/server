package com.dj.server.api.room.model.dto.request;

import com.dj.server.api.room.service.RoomService;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
public class ChatRoomRequestDTO implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    private String roomId;

    private String name;

    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoomRequestDTO(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public void handleActions(WebSocketSession session, ChatMessageRequestDTO chatMessageRequestDTO, RoomService roomService) {
        if(chatMessageRequestDTO.getType().equals(ChatMessageRequestDTO.MessageType.ENTER)) {
            sessions.add(session);
            chatMessageRequestDTO.setMessage(chatMessageRequestDTO.getSender() + " 님이 입장했습니다.");
        }
        sendMessage(chatMessageRequestDTO, roomService);

    }

    public <T> void sendMessage(T message, RoomService roomService) {
        sessions.parallelStream().forEach(session -> roomService.sendMessage(session, message));
    }

}
