package com.dj.server.common.config.websocket;

import com.dj.server.api.room.controller.RoomController;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;


/**
 *
 *
 * pub / sub 방식을 구현합니다
 * pub / sub 방식을 이용하면 구독자 관리가 알아서 되므로
 * 웹소켓 세션 관리가 필요 없어 집니다.
 * 또한 발송의 구현도 알아서 해결되므로 일일이 클라이언트에게 메시지를 발송하지 않아도 됩니다.
 *
 */

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    /**
     *
     * websocket 의 prefix를 설정합니다.
     *
     * @param config
     */

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub");
        config.setApplicationDestinationPrefixes("/pub");
    }

    /**
     *
     * 웹소켓의 EndPoint 를 설정합니다
     *
     * ws://baseUrl:port/addEndpoint
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp").setAllowedOrigins("*").withSockJS();
    }
}
