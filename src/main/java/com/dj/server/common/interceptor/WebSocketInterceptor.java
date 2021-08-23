package com.dj.server.common.interceptor;

import com.dj.server.api.websocket.service.ChatRoomService;
import com.dj.server.api.websocket.service.ChatService;
import com.dj.server.common.exception.member.MemberException;
import com.dj.server.common.exception.member.MemberPermitErrorCode;
import com.dj.server.common.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.security.Principal;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;
    private final ChatRoomService chatRoomService;
    private final ChatService chatService;

    /**
     * 웹소켓을 통해 들어온 요청을 처리하기 전에 실행되는 메서드.
     *
     * if (웹소켓 연결요청이 왔을 경우)
     *     클라이언트가 유효한 jwt token을 가지고 있는지 검사합니다.
     *
     * else if (채팅방 구독 요청이 왔을 경우)
     *      header에서 구독 destination 정보를 얻고 roomId를 추출합니다.
     *
     * else if (웹소켓 연결종료 요청이 왔을 경우)
     *
     * @param message 전달하고자 하는 메시지
     * @param channel 사용되지 않음
     * @return 실제 메시지
     */
    @Override
    public Message<?> preSend(@Nonnull Message<?> message, @Nonnull MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.CONNECT == accessor.getCommand()) {
            String accessToken = accessor.getFirstNativeHeader("access_token");
            if (!jwtUtil.isValidAccessToken(accessToken))
                throw new MemberException(MemberPermitErrorCode.ACCESS_TOKEN_EXPIRED);

        } else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
            String roomId = chatService.getRoomId(Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidRoomId"));
            // 채팅방에 들어온 클라이언트 sessionId를 roomId와 매핑합니다. (나중에 특정 세션이 어떤 채팅방에 들어가 있는지 알기 위함)
            String sessionId = (String) message.getHeaders().get("simpSessionId");
            chatRoomService.setUserEnterInfo(sessionId, roomId);
            chatRoomService.plusUserCount(roomId);
            String name = Optional.ofNullable((Principal) message.getHeaders().get("simpUser")).map(Principal::getName).orElse("UnknownUser");

            log.info("SUBSCRIBED {}, {}", name, roomId);

        } else if (StompCommand.DISCONNECT == accessor.getCommand()) {
            String sessionId = (String) message.getHeaders().get("simpSessionId");
            String roomId = chatRoomService.getUserEnterRoomId(sessionId);
            chatRoomService.minusUserCount(roomId);
            String name = Optional.ofNullable((Principal) message.getHeaders().get("simpUser")).map(Principal::getName).orElse("UnknownUser");
            chatRoomService.removeUserEnterInfo(sessionId);

            log.info("DISCONNECTED {}, {}", sessionId, roomId);
        }

        return message;
    }
}













