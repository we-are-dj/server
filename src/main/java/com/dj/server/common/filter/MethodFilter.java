package com.dj.server.common.filter;

import com.dj.server.common.exception.common.BizException;
import com.dj.server.common.exception.common.GeneralErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * prevent OPTIONS, TRACE http method
 *
 * @author Informix
 * @since 0.0.1
 * @created 2021-09-12
 */
@Component
public class MethodFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equals("OPTIONS")) throw new BizException(GeneralErrorCode.NOT_ALLOW_OPTIONS);
        if (request.getMethod().equals("TRACE")) throw new BizException(GeneralErrorCode.NOT_ALLOW_TRACE);
        filterChain.doFilter(request, response);
    }
}
