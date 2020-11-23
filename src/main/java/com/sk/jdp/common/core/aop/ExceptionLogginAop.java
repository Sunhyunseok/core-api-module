package com.sk.jdp.common.core.aop;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class ExceptionLogginAop {
	
	@AfterThrowing(value="execution(* com.sk.jdp.common..*Controller.*(..))",throwing="exception")
	public void writeFailLog(JoinPoint joinPoint, Exception exception ) {
		
		try {
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			log.error("request error uri {}", request.getRequestURI());
			log.error("request error Referer {}", request.getHeader("Referer"));
			
			Enumeration<String> params = request.getParameterNames();
			while(params.hasMoreElements()) {
				String paramName = params.nextElement();
				String paramValue = request.getParameter(paramName);
				log.error("request param {}:{}", paramName, paramValue);
			}
			if(request.getContentType()!=null && request.getContentType().contains("multipart/form-data")) {
				return;
			}
			Object[] arg = joinPoint.getArgs();
			
			if(arg!=null&&arg.length>0) {
				for(Object o:arg) {
					log.error("request body{}",o.toString());
				}
			}
		} catch(Exception e) {
			log.error("writeFailLog Error",e);
		}

	}
}
