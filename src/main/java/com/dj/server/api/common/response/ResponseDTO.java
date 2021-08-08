package com.dj.server.api.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 *
 * 제네릭 파라매터 를 사용하여 여러 타입을 받으면서
 * 어떤 데이터가 와도 Json 으로 변환하여 공통된 클래스로
 * 데이터를 전달 해 주는 클래스
 *
 * @author JaeHyun
 * @created 2021-08-04
 * @since 0.0.1
 * @param <T> 주로 Entity에 대한 ResponseDTO가 타입 파라매터로 들어옵니다.
 *
 */

@ToString
@Getter
@RequiredArgsConstructor
public class ResponseDTO<T> {

    private final T data;
    private final String message;

}
