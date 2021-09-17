package com.dj.server.common.config.common;

import com.dj.server.api.properties.service.PropertyService;
import com.dj.server.common.filter.CountryFilter;
import com.dj.server.common.interceptor.JwtAuthInterceptor;
import com.dj.server.common.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Jwt 인증을 감시하는 인터셉터의 설정 및
 * Jwt 인증을 지속적으로 확인
 *
 * @author Informix
 * @created 2021-08-04
 * @since 0.0.1
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtUtil jwtUtil;
    private final PropertyService propertyService;

    private final String[] INTERCEPTOR_WHITE_LIST = {
            "/v1/login/oauth2/kakao",
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtAuthInterceptor(jwtUtil, propertyService))
                .addPathPatterns("/v1/**")
                .excludePathPatterns(INTERCEPTOR_WHITE_LIST);
    }

    @Bean
    public FilterRegistrationBean<CountryFilter> countryFilter() {
        FilterRegistrationBean<CountryFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CountryFilter());
        registrationBean.setOrder(0);

        return registrationBean;
    }
}