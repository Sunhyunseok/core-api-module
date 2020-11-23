package com.sk.jdp.common.core.handler;

import java.time.LocalDateTime;
import java.util.StringJoiner;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sk.jdp.common.core.response.BizResponseCode;
import com.sk.jdp.common.core.response.ResponseObject;
import com.sk.jdp.common.core.response.ResponseStatusObject;

@RestControllerAdvice
public class HttpExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(HttpExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseObject handleBindException(HttpServletRequest request, Exception e) {
		ResponseStatusObject responseStatus = new ResponseStatusObject();
		logger.error(e.getMessage());
		responseStatus.setRspTime(LocalDateTime.now());
		responseStatus.setReqUri(request.getRequestURI());
		responseStatus.setRspCode(BizResponseCode.REQUEST_PARAMETER_ERROR.getCode());
		responseStatus.setRspMessage(e.getMessage());
		return new ResponseObject(responseStatus);	
	}
	
	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseObject handleBindException(HttpServletRequest request, BindException e) {
		ResponseStatusObject responseStatus = new ResponseStatusObject();
		BindingResult bindingResult = e.getBindingResult();
		StringJoiner message = new StringJoiner("");
		if(bindingResult.getGlobalErrorCount()>0) {
			message.add(bindingResult.getGlobalError().getDefaultMessage());
			logger.debug("{} .class의 {}", bindingResult.getObjectName(), bindingResult.getGlobalError().getDefaultMessage());
		} else {
			message.add(bindingResult.getFieldError().getField());
			message.add("은(는)");
			message.add(bindingResult.getFieldError().getDefaultMessage());
			logger.debug("{} .class 의 {} 은(는) {}",bindingResult.getObjectName(),bindingResult.getFieldError().getField(),bindingResult.getFieldError().getDefaultMessage());
		}
		responseStatus.setRspTime(LocalDateTime.now());
		responseStatus.setReqUri(request.getRequestURI());
		responseStatus.setRspCode(BizResponseCode.REQUEST_PARAMETER_ERROR.getCode());
		responseStatus.setRspMessage(e.getMessage());
		return new ResponseObject(responseStatus);
	}
}
