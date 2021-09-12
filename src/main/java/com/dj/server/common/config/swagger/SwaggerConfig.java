package com.dj.server.common.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * 스웨거 관련 설정을하는 클래스 입니다
 * 스웨거 관련 설정과 어노테이션은
 *
 * https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Annotations
 * 를 참고하세요.
 *
 * @author JaeHyun
 * @created 2021-08-04
 * @since 0.0.1
 */

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    private final String title = "WE-ARE-DJ API Docs";
    private final String description = "API docs";
    private final String basePackage = "com.dj.server.api";
    private final String pathUri = "/v1/**";
    private final String version = "v1";

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .build();
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(title)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.ant(pathUri))
                .build()
                .apiInfo(apiInfo());
    }
}
