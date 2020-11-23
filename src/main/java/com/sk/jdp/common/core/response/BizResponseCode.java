package com.sk.jdp.common.core.response;

import lombok.Getter;

@Getter
public enum BizResponseCode {
	OK("0000","성공"),
	REQUEST_PARAMETER_ERROR("4001", "요청 매개변수 오류"),
    PROCESS_ERROR("5000", "처리 중 오류")
    ;
	
	private final String code;
	private final String message;
	
	BizResponseCode(String code, String message){
		this.code = code;
		this.message = message;
	}
	
}
