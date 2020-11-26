package com.sk.jdp.common.core.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseErrorObject {
	/* 에러 코드 */
	private String errorCode;
	/* 에러 메시지 */
	private String errorMessage;
	

}
