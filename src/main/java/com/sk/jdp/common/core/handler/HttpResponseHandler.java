package com.sk.jdp.common.core.handler;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.sk.jdp.common.core.model.BaseObject;
import com.sk.jdp.common.core.model.response.BizResponseCode;
import com.sk.jdp.common.core.model.response.PaginatedResponse;
import com.sk.jdp.common.core.model.response.ResponseObject;
import com.sk.jdp.common.core.model.response.ResponseStatusObject;

@RestControllerAdvice
public class HttpResponseHandler implements ResponseBodyAdvice<Object> {
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	/**
	 * JSON 처리
	 */
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		
		if(body == null||
				body instanceof BaseObject ||
				body instanceof PaginatedResponse ||
				(body instanceof List &&
						((List<?>) body).stream().noneMatch((o -> !(o instanceof BaseObject))))) {
			ResponseStatusObject responseStatus = new ResponseStatusObject();
			responseStatus.setRspCode(BizResponseCode.OK.getCode());
			responseStatus.setRspMessage("success");
			responseStatus.setRspTime(LocalDateTime.now());
			responseStatus.setReqUri(request.getURI().toString());
			
			return new ResponseObject(responseStatus, body);
		} else {
			return body;
		}
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseObject handleApiErrorException(HttpServletRequest request, Exception ex) {
		ResponseStatusObject responseStatus = new ResponseStatusObject();
		responseStatus.setRspTime(LocalDateTime.now());
		responseStatus.setReqUri(request.getRequestURI());
		responseStatus.setRspCode(BizResponseCode.PROCESS_ERROR.getCode());
		responseStatus.setRspMessage("시스템 점검 중 입니다.");
		
		ex.printStackTrace();
		
		return new ResponseObject(responseStatus);
	}
}
