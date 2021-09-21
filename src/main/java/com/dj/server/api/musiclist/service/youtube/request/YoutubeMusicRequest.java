package com.dj.server.api.musiclist.service.youtube.request;

import com.dj.server.api.musiclist.model.vo.youtube.YoutubeData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * 실제 Youtube Data api 요청을 위한 클래스입니다.
 *
 * @author Informix
 * @created 2021-09-20
 * @since 0.0.1
 */
@Component
public class YoutubeMusicRequest {

    @Value("${YT-key}")
    private String YTapiKey;

    public YoutubeData search(String keyword) throws JsonProcessingException {

        HttpHeaders header = new HttpHeaders();
        header.add("Content-type", MediaType.APPLICATION_JSON + ";charset=UTF-8");

        UriComponents components = UriComponentsBuilder.fromHttpUrl("https://www.googleapis.com/youtube/v3/search")
                .queryParam("part", "snippet")
                .queryParam("key", YTapiKey)
                .queryParam("maxResults", "5")
                .queryParam("q", keyword)
                .queryParam("safeSearch", "strict")
                .build(false);

        URI uri = components.toUri();

        HttpEntity<String> youtubeMusicRequest = new HttpEntity<>(header);
        HttpEntity<String> result = new RestTemplate().exchange(
                uri,
                HttpMethod.GET,
                youtubeMusicRequest,
                String.class);

        return getBody(result);
    }

    private YoutubeData getBody(HttpEntity<String> result) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper.readValue(result.getBody(), YoutubeData.class);
    }
}