package com.dj.server.common.interceptor;


import com.dj.server.common.exception.common.BizException;
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

@Slf4j
@RequiredArgsConstructor
@Component
public class WebsocketInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String accessToken = accessor.getFirstNativeHeader("access_token");

        if (StompCommand.CONNECT == accessor.getCommand()) { // 연결
//            validateWebSocketToken(accessToken);
            log.info("연결");
        } else if (StompCommand.SUBSCRIBE == accessor.getCommand()) { // 입장

        } else if (StompCommand.SEND == accessor.getCommand()) { //보내기
//            validateWebSocketToken(accessToken);

        } else if(StompCommand.DISCONNECT == accessor.getCommand()) {
            log.info("연결해제");
        }
        return message;
    }

    private void validateWebSocketToken(String accessToken) {
        if (!jwtUtil.isValidAccessToken(accessToken))
            throw new BizException(MemberPermitErrorCode.ACCESS_TOKEN_EXPIRED);
    }


}
