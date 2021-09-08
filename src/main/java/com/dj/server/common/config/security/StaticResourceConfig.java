
package com.dj.server.common.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web.resources.add-mappings: false
 * 설정으로 인해 스프링이 자동으로 세팅해주는 정적자원 경로 설정들이 모두 비활성화되었으므로
 * 정적자원에 대한 요청을 직접 설정하는 목적으로 사용됩니다.
 *
 * @author informix
 * @since 0.0.1
 */
@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    /**
     *  addResourceHandler("/files/**")
     * '/files' 이하의 모든 요청을 정적 자원에 대한 요청으로 인식하게 합니다.
     *
     * .addResourceLocations("classpath:/static/")
     * 정적 자원이 위치한 디렉토리를 설정합니다.
     *
     * 예시: addResourceHandler("/files/**") 로 설정해서
     * [GET] /files/foo.txt' 요청이 가능해졌다면 'classpath:/static/foo.txt'의 자원을 가져오게 됩니다.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui.html") .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**") .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/csrf") .addResourceLocations("classpath:/META-INF/resources/csrf/");
    }
}
