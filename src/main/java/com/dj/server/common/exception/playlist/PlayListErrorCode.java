package com.dj.server.common.exception.playlist;

import org.springframework.http.HttpStatus;

/**
 * PlayList 와 관련된 작업 수행 중 발생할 수 있는 다양한 에러들을 하나로 묶어 관리하는 인터페이스.
 * @see PlayListCrudErrorCode
 *
 * @author JaeHyun
 * @created 2021-08-16
 * @since 0.0.1
 */

public interface PlayListErrorCode {
    String getMsg();
    Integer httpErrorCode();
    HttpStatus getHttpStatus();
}