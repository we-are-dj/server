package com.dj.server.common.config.websocket;

import com.dj.server.common.interceptor.WebSocketInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @EnableWebSocketMessageBroker WebSocket 서버 활성화
 * WebSocketMessageBrokerConfigurer 웹 소켓 연결을 구성하기 위한 메서드 구현 및 제공
 *
 * @author Informix
 * @created 2021-08-23
 * @since 0.0.1
 */
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final WebSocketInterceptor webSocketInterceptor;

    /**
     * 클라이언트가 웹소켓 서버에 연결하는 데 사용할 웹 소켓 엔드포인트를 등록하는 메서드.
     *
     * withSockJS(): 웹소켓을 지원하지 않는 브라우저에 Fallback 활성화.
     *               (sock.js를 통해 낮은 버전의 브라우저에서도 ws를 사용할 수 있도록 설정합니다.)
     *
     * @param registry Stomp 엔드포인트 설정을 돕는 파라메터.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }

    /**
     * 한 클라이언트에서 다른 클라이언트로 메시지를 라우팅하는데 사용될 메시지 브로커를 구성하는 메서드.
     *
     * config.setApplicationDestinationPrefixes("/pub")
     * -   서버가 클라이언트로부터 넘어온 메시지를 받을 controller의 prefix를 "/pub"으로 설정합니다.
     *
     * registry.enableSimpleBroker("/sub")
     * -  "/sub"으로 시작되는 메시지가 메시지 브로커로 라우팅 되도록 정의합니다.
     *     메시지 브로커는 특정 주제를 구독중인, 연결된 모든 클라이언트에게 메시지를 broadcast하는 역할을 맡습니다.
     *
     * @param config 메시지 브로커를 구성하는 것을 돕는 파라메터.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/pub");
        config.enableSimpleBroker("/sub");
    }

    /**
     * // WebSocketInterceptor가 Websocket 앞단에서 token을 체크할 수 있도록 설정합니다.
     * @param registration 채널 구성을 돕는 파라메터
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(webSocketInterceptor);
    }
}
