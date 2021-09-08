package com.dj.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @link https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/EnableAspectJAutoProxy.html
 * EnableAspectJAutoProxy ->  AspectJ의 주석으로 표시된 구성 요소 처리 지원을 활성화합니다
 *
 * @author ServerDeveloper
 * @since 0.0.1
 */
@EnableAspectJAutoProxy
@SpringBootApplication
public class WeAreDjApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeAreDjApplication.class, args);
    }

}
