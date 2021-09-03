package com.dj.server.api.room.service;

import com.dj.server.api.room.model.dto.request.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, ChatMessageDTO messageDTO) {
        redisTemplate.convertAndSend(topic.getTopic(), messageDTO);
    }

}
