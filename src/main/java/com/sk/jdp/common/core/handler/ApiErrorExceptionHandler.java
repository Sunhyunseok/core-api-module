package com.sk.jdp.common.core.handler;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sk.jdp.common.core.exception.ApiErrorException;
import com.sk.jdp.common.core.model.response.BizResponseCode;
import com.sk.jdp.common.core.model.response.ResponseObject;
import com.sk.jdp.common.core.model.response.ResponseStatusObject;




@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class ApiErrorExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(ApiErrorExceptionHandler.class);
	
	@Autowired
	private MessageSourceAccessor messageSource;
	
	
	/**
	 * request와 error를 전달 받아 ResponseObject 타입으로 리턴한다.
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ApiErrorException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseObject handleApiErrorException(HttpServletRequest request, ApiErrorException ex) {
		ResponseStatusObject responseStatus = new ResponseStatusObject();
		responseStatus.setRspTime(LocalDateTime.now());
		responseStatus.setReqUri(request.getRequestURI());
		
		try {
			responseStatus.setRspCode(ex.getRspCode());
			
			if (ex.getRspMessage().isEmpty()) {
				try {
					responseStatus.setRspMessage(messageSource.getMessage(ex.getRspCode()));
				} catch(NoSuchMessageException e) {
					responseStatus.setRspMessage(e.getMessage());
				}
			} else {
				responseStatus.setRspMessage(ex.getRspMessage());
			}
			
		} catch(Exception e) {
			log.error("apiErrorException Handler", e);
			responseStatus.setRspCode(BizResponseCode.PROCESS_ERROR.getCode());
			responseStatus.setRspMessage("시스템 점검 중입니다.");
		}
		return new ResponseObject(responseStatus);
	}
	
		
}
