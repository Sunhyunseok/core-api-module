package com.sk.jdp.common.core.handler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sk.jdp.common.core.response.ResponseErrorObject;
import com.sk.jdp.common.core.response.ResponseObject;
import com.sk.jdp.common.core.response.ResponseStatusObject;


/**
 * @ClassName ValidatedErrorExceptionHandler
 * @Description spring validation 에 의한 BindException 발생 시 통합메시지 처리
 */
@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class ValidatedErrorExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(ValidatedErrorExceptionHandler.class);
	
	@Autowired
	private MessageSourceAccessor messageSource;
	
	
	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseObject handleBindException(HttpServletRequest request, BindException ex) {
		return stringToBindingResult(request, ex.getBindingResult());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseObject handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
		return stringToBindingResult(request, ex.getBindingResult());
	}
	
	/**
	 * Pattern 등의 Validation 실패
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseObject handleConstraintViolationException(HttpServletRequest request, ConstraintViolationException ex) {
		
		List<String> messages = ex.getConstraintViolations().stream().map(item -> item.getMessage()).collect(Collectors.toList());
		return responseObjectToList(request.getRequestURI(), messages);
	}
	
	/**
	 * RequestParam 등의 요청 데이터의 필드 누락
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseObject handleMissingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException ex) {
		List<String> messages = new ArrayList<>();
		messages.add(ex.getParameterName() + messageSource.getMessage("valid.validationtest.4001"));
		
		return responseObjectToList(request.getRequestURI(), messages);
	}
	
	/**
	 * BindingResult 정보를 이용하여 responseObjectToList 메소드 호출
	 * @param uri
	 * @param bindingResult
	 * @return
	 */
	protected ResponseObject stringToBindingResult(HttpServletRequest request, BindingResult bindingResult) {
		return responseObjectToList(request.getRequestURI(), bindingResult.getFieldErrors().stream().map(item -> item.getDefaultMessage()).collect(Collectors.toList()));
	}
	
	/**
	 * uri와 메세지 리스트를 전달받아 ResponseObject 타입으로 리턴한다.
	 * @param uri
	 * @param messages
	 * @return
	 */
	protected ResponseObject responseObjectToList(String uri, List<String> messages) {
		ResponseStatusObject responseStatus = new ResponseStatusObject();
		responseStatus.setRspTime(LocalDateTime.now());
		responseStatus.setReqUri(uri);
		try {
			List<ResponseErrorObject> errors = new ArrayList<>();
			
			for(String msg:messages) {
				ResponseErrorObject errorObject = new ResponseErrorObject();
				if(msg.startsWith("valid")) {
					errorObject.setErrorCode(msg);
					try {
						errorObject.setErrorMessage(messageSource.getMessage(msg));
					} catch(NoSuchMessageException e) {
						errorObject.setErrorMessage(e.getMessage());
					}
				} else {
					errorObject.setErrorCode("valid.default");
					errorObject.setErrorMessage(msg);
				}
				errors.add(errorObject);
			}
			
			responseStatus.setErrors(errors);
			responseStatus.setRspCode("error.99999");
			responseStatus.setRspMessage("validated exception");
		} catch(Exception e) {
			log.error("ValidatedErrorException handler",e);
			responseStatus.setRspCode("error.99999");
			responseStatus.setRspMessage("system error(validated exception)");
		}
		return new ResponseObject(responseStatus);
	}
}
