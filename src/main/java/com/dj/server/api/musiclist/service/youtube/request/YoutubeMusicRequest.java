package com.dj.server.api.musiclist.service.youtube.request;

import com.dj.server.api.musiclist.model.vo.youtube.YoutubeData;
import com.dj.server.common.jwt.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

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

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://www.googleapis.com/youtube/v3/search")
                .queryParam("part", "snippet")
                .queryParam("key", YTapiKey)
                .queryParam("order", "relevance")
                .queryParam("maxResults", "25")
                .queryParam("q", UriUtils.encode(keyword, "UTF-8"))
                .queryParam("safeSearch", "strict");

        HttpEntity<String> youtubeMusicRequest = new HttpEntity<>(header);
        HttpEntity<String> result = new RestTemplate().exchange(
                builder.toUriString(),
                HttpMethod.GET,
                youtubeMusicRequest,
                String.class);
        return getBody(result);
    }

    private YoutubeData getBody(HttpEntity<String> result) throws JsonProcessingException {
        System.out.println(result.getBody());
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper.readValue(result.getBody(), YoutubeData.class);
    }
}
