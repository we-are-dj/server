package com.dj.server.config.interceptor;

import com.dj.server.api.member.service.MemberArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Jwt 인증을 감시하는 인터셉터의 설정 및
 * Jwt 인증을 지속적으로 확인
 *
 * @author Informix
 * @created 2021-08-04
 * @since 0.0.1
 */
@Configuration
public class JwtConfig implements WebMvcConfigurer {
    private final String[] INTERCEPTOR_WHITE_LIST = {
            "/",
            "/signUp",
            "/signIn",
            "/signUp/**",
            "/signIn/**",
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtAuthInterceptor())
                //.addPathPatterns("/**") // URI 전체를 인터셉터로 제어
                .excludePathPatterns(INTERCEPTOR_WHITE_LIST);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new MemberArgumentResolver());
    }

}