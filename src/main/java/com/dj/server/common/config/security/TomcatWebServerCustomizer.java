package com.dj.server.common.config.security;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 톰캣 설정 커스터마이징 클래스
 *
 * @author Informix
 * @created 2021-09-07
 * @since 0.0.1
 */
@Configuration
public class TomcatWebServerCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    /**
     * 1. relaxedPathChars, relaxedQueryChars:
     * (특수문자가 포함되어) 잘못된 url 경로 또는 (특수문자가 포함되어) 잘못된 Query Param를 실어보내는 요청이 들어올 경우에
     * 발생하는 IllegalArgumentException을 ControllerAdvice로 처리하기 위하여
     * 톰캣에 해당 특수문자를 제거하는 옵션을 추가합니다.
     *
     * 이 옵션이 없다면 톰캣의 Bad Request 에러 메시지가
     * 사용자에게 완전히 노출되어 보이게 되므로 위험합니다.
     *
     * relaxedPathChars: uri에 해당
     * relaxedQueryChars: queryString에 해당 (url중 ?로 시작하는 뒷부분)
     *
     * 예시: localhost:8080/||||index[][].php?s=/Index/\think\app/invokefunction&function=call_user_func_array&vars[0]=md5&vars[1][]=HelloThinkPHP21
     * 결과: localhost:8080/index.php?s=/Index/\think\app/invokefunction&function=call_user_func_array&vars0=md5&vars1=HelloThinkPHP21
     *
     * 2. scheme, secure:
     * 톰캣 Connector 설정변경을 통해 secure 환경으로 명시하여,
     * 톰캣으로 하여금 http 요청이 https 로 들어오도록 합니다.
     *
     * 예시: http://localhost:8080/... 로 요청이 오면 https로 리다이렉트시키는 것이 아니라, 처음부터 https로 요청이 들어오도록 톰캣이 요청을 조작합니다.
     * @see org.springframework.security.web.header.writers.HstsHeaderWriter # secure가 true이면
     *                                                                          SecureRequestMatcher.matches(HttpsServleetRequest request) 메소드가
     *                                                                          true를 반환하게 되어 HTTPS 프로토콜 통신이 아님에도 불구하고
     *                                                                          HSTS 설정이 적용됩니다.
     */
    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.addConnectorCustomizers(connector -> connector.setProperty("relaxedPathChars", "[]|"));
        factory.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "<>[\\]^`{|}"));
        factory.addConnectorCustomizers(connector -> connector.setProperty("scheme", "https"));
        factory.addConnectorCustomizers(connector -> connector.setProperty("secure", "true"));
    }
}