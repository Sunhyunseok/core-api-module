package com.sk.jdp.common.core.handler;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.sk.jdp.common.core.response.ResponseObject;
import com.sk.jdp.common.core.response.ResponseStatusObject;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class FileErrorExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(FileErrorExceptionHandler.class);
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected ResponseObject handleBindException(HttpServletRequest request, MaxUploadSizeExceededException ex) {
		ResponseStatusObject responseStatus = new ResponseStatusObject();
		responseStatus.setRspTime(LocalDateTime.now());
		responseStatus.setReqUri(request.getRequestURI());
		responseStatus.setRspCode("error.file");
		responseStatus.setRspMessage(ex.getMessage());
		log.error("fileErrorExecption handler - maxsize:{}, message:{}",ex.getMaxUploadSize(),ex.getMessage());
	
		return new ResponseObject(responseStatus);
		
	}

}
