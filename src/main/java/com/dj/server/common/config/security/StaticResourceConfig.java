
package com.dj.server.common.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
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
     * addRedirectViewController(요청 url, 리다이렉트 url)
     * 어떤 요청이 들어오면 다른 경로로 리다이렉트시킵니다.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/static/api/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
        registry.addRedirectViewController("/static/api/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
        registry.addRedirectViewController("/static/api/swagger-resources", "/swagger-resources");
    }
    /**
     *  addResourceHandler("/files/**")
     * '/files' 이하의 모든 요청을 정적 자원에 대한 요청으로 인식하게 합니다.
     *
     * .addResourceLocations("classpath:/static/")
     * 정적 자원이 위치한 디렉토리를 설정합니다. 예를 들어
     * [GET] /files/foo.txt' 요청이라면 'classpath:/static/foo.txt' 를 의미합니다.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/**").addResourceLocations("classpath:/static/api/").setCachePeriod(60 * 60);
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/").setCachePeriod(60 * 60);
        registry.addResourceHandler("/errors/**").addResourceLocations("classpath:/static/errors/").setCachePeriod(60 * 60);
    }
}
