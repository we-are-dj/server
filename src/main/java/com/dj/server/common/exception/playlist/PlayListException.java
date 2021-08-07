package com.dj.server.common.exception.playlist;


/**
 * 서버 실행중 발생하는 PlayList Entity와 관련된 에러를 예외처리하기 위한 클래스.
 *
 * @see com.dj.server.common.exception.member.MemberErrorCode
 * @author Informix
 * @created 2021-08-03 Tue
 */
public class PlayListException extends RuntimeException {

    /**
     * @serialField RuntimeException 의 조상 Throwable이 Serialize를 상속하고 있으므로
     *              직렬화 시도시 serialVersionUID의 값이 임의 변경되지 못하도록 명시하여
     *              PlayListException class의 정확한 errorCode를 가져올 수 있도록 사용할 수 있다.
     */
    private static final long serialVersionUID = 1L;
    private final PlayListErrorCode errorCode;

    /**
     * super(errorCode의 상세설명 메시지, http 통신 중 에러가 발생한 원인)
     * @param errorCode PlayList Entity와 관련된 에러정보를 저장
     */
    public PlayListException(PlayListErrorCode errorCode) {
        super(errorCode.getMsg(), new Throwable(errorCode.getHttpStatus().toString()));
        this.errorCode = errorCode;
    }

    /**
     * @return PlayList Entity와 관련된 에러 정보
     */
    public PlayListErrorCode getErrorCode() {
        return errorCode;
    }
}