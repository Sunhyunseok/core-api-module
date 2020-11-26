package com.sk.jdp.common.core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



/**
 * @ClassName CommonLoggingAop.java
 * @Description 공통 로깅 AOP 설정
 */
@Aspect
@Component
public class CommonLoggingAop {
    private static final Logger logger = LoggerFactory.getLogger(CommonLoggingAop.class);

    @Pointcut("execution(* com.sk.jdp.common..*Service.*(..))")
    public void serviceLogPointcut() {
    }

    @Before("serviceLogPointcut()")
    public void beforeLogging(JoinPoint joinPoint) {
        logger.info("Aspect before service : {} / {}()", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
    }

    @Around("serviceLogPointcut()")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.info("Aspect start service : {} / {}()", proceedingJoinPoint.getSignature().getDeclaringTypeName(), proceedingJoinPoint.getSignature().getName());
        logger.info("Aspect finished service : {} / {}()", proceedingJoinPoint.getSignature().getDeclaringTypeName(), proceedingJoinPoint.getSignature().getName());

        Object result = proceedingJoinPoint.proceed();
        return result;
    }

}
