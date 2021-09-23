package com.dj.server.common.aop;

import com.dj.server.api.common.request.LoggingSupport;
import com.dj.server.common.filter.CountryFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * 컨트롤러의 앞 뒤의 로그를 기록하는
 * Logging Aop 클래스
 *
 * @created 2021-08-06
 * @author JaeHyun
 * @since 0.0.1
 *
 */

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class LoggingAop {



    /**
     * Around 는 target 메서드를 감싸는 Advice 입니다.
     * 즉, 앞과 뒤에 모두 영향을 미칠 수 있습니다.
     * 또한 Around 는 target 을 실행할 지 아니면 바로 반환할지도 정할 수 있습니다.
     */
    @Around("execution(* com.dj.server.api..*Controller.*(..)) && !execution(* com.dj.server.api.room.controller.SocketController.*(..))")
    public Object loggingParameters(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        // StringBuilder logBuilder = new StringBuilder();
        LoggingSupport<Object> loggingSupport = new LoggingSupport<>();

        String className = proceedingJoinPoint.getTarget().getClass().getName();
        String methodName = proceedingJoinPoint.getSignature().getName();

        setUserIP(loggingSupport);
        loggingSupport.setClassName(className);
        loggingSupport.setMethodName(methodName);

        //들어온 값들 로그 남기기
        for(Object object : proceedingJoinPoint.getArgs()) {
            loggingSupport.getData().add(object);
        }
        log.info(loggingSupport.toString());

        //원래 실행해야 하는 메소드 실행
        Object object = proceedingJoinPoint.proceed();

        log.info(object.toString());

        return object;
    }

    private void setUserIP(LoggingSupport<Object> loggingSupport) {
        HttpServletRequest request =  ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        loggingSupport.setUserIp(CountryFilter.getClientIp(request));
    }

}
