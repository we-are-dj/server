package com.dj.server.config.security;
import org.springframework.context.annotation.Bean;
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
 * @author Informix
 * @created 2021-08-03 Tue
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * cors().configurationSource(corsConfigurationSource())
     * CORS 옵션에 대한 설정으로 corsConfigurationSource()를 사용함을 명시
     *
     * csrf().disable()
     * csrf 비활성화됨
     *
     * authorizeRequest().requestMachers(CorsUtils:isPreFilghtRequest).permitAll()
     * 특정 —하위에 기술된 CorsConfigration에서 허용하도록 추가된— http 사전 요청에 대하여 cors 승인되도록 설정됨
     *
     * anyRequest().permitAll()
     * 모든 요청에 대하여 (인증된 사용자인지 검사하지 않고) 요청 데이터 등을 반환하도록 설정됨
     *
     * and().formLogin().disable()
     * form based login 비활성화됨
     * (Spring Security auth login 화면 비활성화)
     *
     * @since 0.0.1
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors()
                    .configurationSource(corsConfigurationSource())
                    .and()
                    .csrf()
                    .disable()
                    .authorizeRequests()
                    .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                // .antMatchers("/put").hasAnyRole("ADMIN")
                // .antMatchers("/**").denyAll()
                    .anyRequest().permitAll()
                    .and()
                    .formLogin()
                    .disable();
    }

    /**
     * configuration.addAllowedOrigin("*")
     * 모든 요청 출처(origion)에 대해 승인
     *
     * configuration.addAllowedMethod("*")
     * 모든 http method 승인 (get, post, put, delete, options etc)
     *
     * configuration.addAllowedHeader("*")
     * 모든 헤더 허용 (어떠한 인증 헤더도 필요치 않음)
     *
     * configuration.setAllowCredentials(true)
     * 다른 origin에 대한 쿠키 저장을 허용함
     * (같은 origin에 대해서는 자동으로 reqeust header에 저장되므로, 이 옵션은 다른 origin에 대한 쿠키를 설정할지 말지 여부에 사용됨)
     *
     * configuration.setMaxAge(3600L)
     * 쿠키를 클라이언트에게 보내기 전에 쿠키의 생존기간을 설정함. 매개변수는 second 단위.
     * 따라서 3600L는 3600초이므로 1시간에 해당
     *
     * source.registerCorsConfiguration("/**", configuration)
     * 특성 URL에 대하여 configration을 적용하여 등록
     *
     * @return configuration가 적용된, 특정 URL 정보
     *
     * @since 0.0.1
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*"); // set http://localhost:3000 etc
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
