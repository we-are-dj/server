package com.dj.server.api.room.model.dto.request;

import com.dj.server.api.room.service.RoomService;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

@Setter
@Getter
public class ChatRoomDTO implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    private String roomId;
    private String name;

   public static ChatRoomDTO create(String name) {
       ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
       chatRoomDTO.roomId = UUID.randomUUID().toString();
       chatRoomDTO.name = name;
       return chatRoomDTO;
   }

}