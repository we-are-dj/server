package com.dj.server.api.websocket.entity;

import com.dj.server.api.websocket.model.dto.ChatRoomDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
public class ChatRoom implements Serializable { // redis에 저장되는 객체들은 Serialize가 가능해야 함, -> Serializable 참조

    private static final long serialVersionUID = 6494678977089006639L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String roomId;

    @Column
    private String roomName;

    @Column
    private String userInterested;

    private long userCount; // 채팅방 인원수

    @Builder
    public ChatRoom(String roomId, String roomName, String userInterested) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.userInterested = userInterested;
    }

    public static ChatRoom createChatRoom(ChatRoomDTO chatRoomDTO) {
        return ChatRoom.builder()
                    .roomId(UUID.randomUUID().toString())
                    .roomName(chatRoomDTO.getRoomName())
                    .userInterested(chatRoomDTO.getUserInterested())
                    .build();
    }

}