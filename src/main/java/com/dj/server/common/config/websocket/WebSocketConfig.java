package com.dj.server.common.config.websocket;

import org.springframework.context.annotation.Configuration;
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
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 클라이언트가 웹소켓 서버에 연결하는 데 사용할 웹 소켓 엔드포인트를 등록하는 메서드.
     * withSockJS(): 웹소켓을 지원하지 않는 브라우저에 Fallback 활성화
     *
     * @param registry Stomp 엔드포인트 설정을 돕는 파라매터.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }

    /**
     * 한 클라이언트에서 다른 클라이언트로 메시지를 라우팅하는데 사용될 메시지 브로커를 구성하는 메서드.
     *
     * registry.enableSimpleBroker("/topic")
     *  -  "/topic" 시작되는 메시지가 메시지 브로커로 라우팅 되도록 정의합니다.
     *     메시지 브로커는 특정 주제를 구독 한 연결된 모든 클라이언트에게 메시지를 broadcast 합니다.
     *
     * @param registry 메시지 브로커를 구성하는 것을 돕는 파라매터.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
    }
}
