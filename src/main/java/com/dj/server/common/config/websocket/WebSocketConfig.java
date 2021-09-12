package com.dj.server.common.config.websocket;

import com.dj.server.common.interceptor.WebsocketInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


/**
 * pub / sub 방식을 구현합니다
 * pub / sub 방식을 이용하면 구독자 관리가 알아서 되므로
 * 웹소켓 세션 관리가 필요 없어 집니다.
 * 또한 발송의 구현도 알아서 해결되므로 일일이 클라이언트에게 메시지를 발송하지 않아도 됩니다.
 */

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    private final WebsocketInterceptor webContentInterceptor;

    /**
     *
     * enableSimpleBroker : 해당 prefix 로 요청이 들어오면 브로커가 관련된 모든 구독자에게 메세지를 보내줍니다.
     * ex) /sub/room/ + roomId -> 현재 roomId 방에 있는 모든 사람들에게
     *
     * setApplicationDestinationPrefixes : 해당 prefix 로 요청이 들어오면 MessageMapping 과 매핑을 시켜줍니다.
     *
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub");
        config.setApplicationDestinationPrefixes("/pub");
    }

    /**
     *
     * addEndPoint : 서버와 연결할 소켓 HTTP URL 입니다
     *
     * withSockJS : stomp 환경으로 변경합니다.
     *
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/we-are-dj").setAllowedOriginPatterns("*").withSockJS();
    }

    /**
     *
     * 소켓 컨트롤러 인터셉터
     *
     */

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(webContentInterceptor);
    }


}
