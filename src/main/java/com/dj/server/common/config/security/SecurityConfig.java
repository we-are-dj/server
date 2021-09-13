package com.dj.server.common.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * WebSecurityConfigurerAdapter를 상속받고
 * EnableWebSecurity를 추가하여 SpringSecurityFilterChain을 활성화합니다.
 * Filter는 SecurityConfig라는 이름으로 FilterChain에 등록됩니다.
 * 해당 Filter는 ApplicationContext에 정의된 Spring Bean이 되어 DI와 Life cycle 기능을 활용할 수 있습니다.
 *
 * # @EnableGlobalMethodSecurity(prePostEnabled = true) @PreAuthorize 애너테이션 설정 활성화
 *
 *
 * @author Informix
 * @author JaeHyun
 * @created 2021-08-03 Tue
 */
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * <p>
     * # configurationSource() <br> 설정은 필요에 따라 추가 삭제될 예정입니다.
     * </p>
     * <p>
     * cors().configurationSource(corsConfigurationSource())  <br>
     * CORS 옵션에 대한 설정으로 corsConfigurationSource()를 사용함을 명시
     * </p>
     * <p>
     * csrf().disable() <br>
     * csrf 비활성화
     * </p>
     * <p>
     * authorizeRequest().requestMachers(CorsUtils:isPreFilghtRequest).permitAll() <br>
     * —하위에 기술된 CorsConfigration에서 허용하도록 추가된— 특정 http 사전 요청에 대하여 cors 승인되도록 설정됨 (401 에러를 발생시키지 않는다.)
     * </p>
     * <p>
     * anyRequest().permitAll() <br>
     * 모든 요청에 대하여 (인증된 사용자인지 검사하지 않고) 요청 데이터 등을 반환하도록 설정됨
     * </p>
     * <p>
     * .headers().httpStrictTransportSecurity().maxAgeInSeconds(31536000) <br>
     * hsts를 활성화하고 hsts 설정 유지 기간을 1년으로 설정합니다.
     * </p>
     * <p>
     * .includeSubDomains(true) <br>
     * 현재 도메인의 하위 도메인까지 포함하여 설정하며,
     * </p>
     * <p>
     * .preload(true) <br>
     * 브라우저가 이 설정을 기억해 http 접속시에 http가 아닌 곧바로 https 통신을 하도록 합니다.
     * </p>
     * <p>
     * .headers().frameOptions().disable() <br>
     * X-Frame-Options 비활성화
     * </p>
     * <p>
     * formLogin().disable() <br>
     * form based login 비활성화됨
     * (Spring Security auth login 화면 비활성화)
     * </p>
     * <p>
     * logout().permitAll() <br>
     * 로그아웃에 대한 모든 요청을 승인합니다.
     * </p>
     *
     * @since 0.0.1
     */
    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security.csrf().disable();

        security.authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest)
                .permitAll()
                .anyRequest().permitAll();

        security.cors().configurationSource(corsConfigurationSource());

        security.headers()
                .httpStrictTransportSecurity()
                .maxAgeInSeconds(31536000)
                .includeSubDomains(true)
                .preload(true);

        security.headers().frameOptions().disable();

        security.formLogin().disable()
                .logout()
                .permitAll();
    }

    /**
     * <p>
     * configuration.addAllowedOriginPattern("*")
     * 모든 요청 출처(origin)에 대해 승인
     * </p>
     * <p>
     * configuration.addAllowedMethod("*")
     * 모든 http method 승인 (get, post, put, patch, delete, options, trace etc)
     * </p>
     * <p>
     * configuration.addAllowedHeader("*")
     * 모든 헤더 허용 (어떠한 인증 헤더도 필요치 않음)
     * </p>
     * <p>
     * configuration.setMaxAge(3600L)
     * 쿠키를 클라이언트에게 보내기 전에 쿠키의 생존기간을 설정합니다. 매개변수는 second 단위.
     * 따라서 3600L는 3600초이므로 1시간에 해당
     * </p>
     * <p>
     * configuration.setAllowCredentials(true)
     * 자격 증명이 헤더에 포함되도록 설정
     * </p>
     * <p>
     * source.registerCorsConfiguration("/**", configuration)
     * 특성 URL에 대하여 configration을 적용하여 등록
     * </p>
     *
     * @return configuration가 적용된 특정 URL 정보
     * @since 0.0.1
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern(CorsConfiguration.ALL);
        configuration.addAllowedMethod(CorsConfiguration.ALL);
        configuration.addAllowedHeader(CorsConfiguration.ALL);
        configuration.setMaxAge(3600L);
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}