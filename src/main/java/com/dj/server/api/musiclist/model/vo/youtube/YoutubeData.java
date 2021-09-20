package com.dj.server.api.musiclist.model.vo.youtube;

import lombok.*;

import java.beans.ConstructorProperties;
import java.util.List;

/**
 * Youtube Data api 요청 결과를 담는 클래스입니다.
 *
 * @author Informix
 * @created 2021-09-20
 * @since 0.0.1
 */
@ToString
@Setter
@Getter
public class YoutubeData {
    private final List<?> items;

    @ConstructorProperties({ "items" })
    public YoutubeData(List<?> items) {
        this.items = items;
    }
}
