package com.dj.server.common.filter;

import com.dj.server.api.common.response.ErrorResponseDTO;
import com.dj.server.common.exception.common.BizException;
import com.dj.server.common.exception.member.enums.MemberPermitErrorCode;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 국가제한 필터
 *
 * @author Informix
 * @author JaeHyun
 * @created 2021-09-08
 * @since 0.0.1
 */
@Slf4j
public class CountryFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!isAcceptableCountry(request.getLocale().toString())) {
            log.info("한국 또는 미국 외의 다른 국가에서 접속을 시도했습니다.");
            log.info("ip:     " + getClientIp((HttpServletRequest) request));
            LocalDateTime time = LocalDateTime.now();
            log.info("time:   " + time.atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            log.info("locale: " + request.getLocale().toString());
            setErrorResponse(HttpStatus.FORBIDDEN, (HttpServletResponse) response, new BizException(MemberPermitErrorCode.NO_PERMIT_COUNTRY));
            return;
        }
        chain.doFilter(request, response);
    }

    /**
     * 클라이언트 ip 로그 기록용
     * @param request HttpServletRequest
     * @return 클라이언트 ip
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) ip = request.getHeader("Proxy-Client-IP");
        if (ip == null) ip = request.getHeader("WL-Proxy-Client-IP");
        if (ip == null) ip = request.getHeader("HTTP_CLIENT_IP");
        if (ip == null) ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (ip == null) ip = request.getRemoteAddr();
        return ip;
    }

    /**
     * 허용할 국가 목록을 설정합니다.
     */
    private boolean isAcceptableCountry(String locale) {
        return locale.equals("ko_KR") || locale.equals("ko") || locale.equals("en_US") || locale.equals("en");
    }

    /**
     * ControllerAdvice가 필터의 에러를 처리해주지 못하기 때문에,
     * 따로 구현됩니다. 해당 메서드는 timestamp를 출력하지 않습니다.
     *
     * @param status HttpStatus
     * @param response HttpServletResponse
     * @param e BizException
     */
    public void setErrorResponse(HttpStatus status, HttpServletResponse response, BizException e) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ErrorResponseDTO responseDTO = ErrorResponseDTO.builder()
                                                       .offDisplayNow()
                                                       .errorCode(status.value())
                                                       .message(e.getMessage())
                                                       .build();
        response.getWriter().write(responseDTO.toString());
    }
}
