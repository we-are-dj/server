package com.dj.server.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 *
 * 컨트롤러의 앞 뒤의 로그를 기록하는
 * Logging Aop 클래스
 *
 * @create 2021-08-06
 * @author JaeHyun
 * @since 0.0.1
 *
 */

@Slf4j
@Aspect
@Component
public class LoggingAop {

    /**
     *
     *
     * Around 는 target 메서드를 감싸는 Advice 입니다.
     * 즉, 앞과 뒤에 모두 영향을 미칠 수 있습니다.
     * 또한 Around 는 target 을 실행할 지 아니면 바로 반환할지도 정할 수 있습니다.
     * 
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */

    @Around("execution(* *..controller.*.*(..))")
    public Object loggingParameters(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        StringBuilder logBuilder = new StringBuilder();

        String className = proceedingJoinPoint.getTarget().getClass().getName();
        String methodName = proceedingJoinPoint.getSignature().getName();

        log.info("Class ==> {}, method ==> {}", className, methodName);

        //들어온 값들 로그 남기기
        for(Object object : proceedingJoinPoint.getArgs()) {
            log.info("Request Data ==> {}", object);
        }

        //원래 실행해야 하는 메소드 실행
        Object object = proceedingJoinPoint.proceed();

        log.info("Response Data ==> {}", object.toString());

        return object;
    }

}
