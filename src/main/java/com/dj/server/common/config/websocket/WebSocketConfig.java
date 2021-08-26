package com.dj.server.common.config.websocket;

import com.dj.server.api.room.controller.RoomController;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final RoomController roomController;


    /**
     *
     * 웹 소켓 프로토콜로 들어오는 URL 을 컨트롤 합니다
     *
     * @param registry
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(roomController, "/ws/chat").setAllowedOrigins("*").
                addHandler(roomController ,"/create/chat").setAllowedOrigins("*");
    }
}
