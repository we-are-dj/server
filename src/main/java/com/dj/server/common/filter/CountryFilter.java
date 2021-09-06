package com.dj.server.common.filter;

import com.dj.server.common.exception.common.BizException;
import com.dj.server.common.exception.member.enums.MemberPermitErrorCode;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class CountryFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String locale = request.getLocale().toString();

        if (!(locale.equals("ko_KR") || locale.equals("en_US"))) {
            log.info("한국 또는 미국 외의 다른 국가에서 접속한 유저가 있습니다.");
            log.info("catch ip: " + getClientIp((HttpServletRequest) request));
            log.info("catch locale: " + locale);
            throw new BizException(MemberPermitErrorCode.NO_PERMIT_COUNTRY);
        }
        chain.doFilter(request, response);
    }

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) ip = request.getHeader("Proxy-Client-IP");
        if (ip == null) ip = request.getHeader("WL-Proxy-Client-IP");
        if (ip == null) ip = request.getHeader("HTTP_CLIENT_IP");
        if (ip == null) ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (ip == null) ip = request.getRemoteAddr();
        return ip;
    }
}
