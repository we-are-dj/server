package com.dj.server.common.exception.playlist;

import lombok.Getter;
import org.springframework.http.HttpStatus;
/**
 * Playlist Entity에 대한 데이터 요청시 발생할 수 있는 에러들을 열거형으로 모아둔 클래스
 *
 * @see PlayListException
 * @author Informix
 * @created 2021-08-03 Tue
 */
@Getter
public enum PlayListCrudErrorCode implements PlayListErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, 404, "플레이목록이 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final int httpErrorCode;
    private final String msg;

    /**
     * @param httpStatus spring의 HttpStatus에 등록되어 있는 http 통신에러코드들 중 하나를
     *                   PlayListErrorCode과 매핑하여, PlayListErrorCode 있는 에러가 실제로 어떤 http 통신 실패를 했는지 알려주는 목적으로 사용됨
     * @param httpErrorCode Http 통신코드번호를 기술하여 가독성을 향상함
     * @param msg 에러 발생시 정확한 원인을 전달하기 위하여 기술함
     */
    PlayListCrudErrorCode(HttpStatus httpStatus, int httpErrorCode, String msg) {
        this.httpStatus = httpStatus;
        this.httpErrorCode = httpErrorCode;
        this.msg = msg;
    }




    public String getMsg() {
        return msg;
    }
    public Integer httpErrorCode() { return httpErrorCode; }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
