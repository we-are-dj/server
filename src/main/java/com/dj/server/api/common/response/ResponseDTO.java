package com.dj.server.api.common.response;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 *
 * 제네릭 파라매터 를 사용하여 여러 타입을 받으면서
 * 어떤 데이터가 와도 Json 으로 변환하여 공통된 클래스로
 * 데이터를 전달 해 주는 클래스
 *
 * @author JaeHyun
 * @created 2021-08-04
 * @since 0.0.1
 * @param <T> --> 주로 Entity 에 대한 ResponseDTO 가 파라매터로 들어오게 됩니다.
 *
 */

@Getter
@RequiredArgsConstructor
public class ResponseDTO<T> {

    private final T data;
    private final String message;
    private final HttpStatus httpStatus;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
