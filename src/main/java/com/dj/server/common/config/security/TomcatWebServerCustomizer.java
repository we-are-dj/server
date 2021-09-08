package com.dj.server.common.config.security;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

/**
 * 톰캣 설정 커스터마이징용 클래스
 *
 * @author Informix
 * @since 0.0.1
 */
@Configuration
public class TomcatWebServerCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    /**
     * (특수문자가 포함되어) 잘못된 url 경로 또는 (특수문자가 포함되어) 잘못된 Query Param를 실어보내는 요청이 들어올 경우에
     * 발생하는 IllegalArgumentException을 ControllerAdvice로 처리하기 위하여
     * 톰캣에 해당 특수문자를 제거하는 옵션을 추가합니다.
     *
     * 이 옵션이 없다면 톰캣의 Bad Request 에러 메시지가
     * 사용자에게 완전히 노출되어 보이게 되므로 위험합니다.
     *
     * 예시: localhost:8080/||||index[][].php?s=/Index/\think\app/invokefunction&function=call_user_func_array&vars[0]=md5&vars[1][]=HelloThinkPHP21
     * 결과: localhost:8080/index.php?s=/Index/\think\app/invokefunction&function=call_user_func_array&vars0=md5&vars1=HelloThinkPHP21
     */
    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.addConnectorCustomizers(connector -> connector.setProperty("relaxedPathChars", "[]|"));
        factory.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "<>[\\]^`{|}"));
    }
}