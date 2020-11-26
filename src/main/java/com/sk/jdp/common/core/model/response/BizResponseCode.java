package com.sk.jdp.common.core.model.response;

import lombok.Getter;

@Getter
public enum BizResponseCode {
	OK("0000","성공"),
	FILE_UPLOAD_ERROR("4000", "파일 업로드 사이즈 초과 오류"),
	REQUEST_PARAMETER_ERROR("4001", "요청 매개변수 오류"),
    PROCESS_ERROR("5000", "처리 중 오류"),
    VALIDATION_ERROR("9999","유효성 검사 오류")
    ;
	
	private final String code;
	private final String message;
	
	BizResponseCode(String code, String message){
		this.code = code;
		this.message = message;
	}
	
}
