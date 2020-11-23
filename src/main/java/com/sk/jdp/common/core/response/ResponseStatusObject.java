package com.sk.jdp.common.core.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseStatusObject {

	private String rspCode;
	
	private String rspMessage;
	
	private LocalDateTime rspTime;
	
	private String reqUri;
	
	private List<ResponseErrorObject> errors;
	
}
