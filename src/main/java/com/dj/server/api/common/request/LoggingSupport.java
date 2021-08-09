package com.dj.server.api.common.request;


import com.google.gson.Gson;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * AOP 에서 로그를 JSON 으로 남기기 위한 도움을 주는 클래스
 *
 * @author JaeHyun
 * @created 2021-08-09
 * @since 0.0.1
 * @param <T> --> Request 에 대한 데이터가 들어옵니다.
 */


@Setter
@Getter
@Component
public class LoggingSupport<T> {

    private final List<T> data = new ArrayList<>();
    private String className;
    private String methodName;


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
