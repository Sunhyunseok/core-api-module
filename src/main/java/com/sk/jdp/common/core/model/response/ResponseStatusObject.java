package com.sk.jdp.common.core.model.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseStatusObject {

	/* 응답 코드 */
	private String rspCode;
	/*응답 메시지*/
	private String rspMessage;
	/* 응답 시간 */
	private LocalDateTime rspTime;
	/* 요청한 주소 */
	private String reqUri;
	/* 에러 리스트 */
	private List<ResponseErrorObject> errors;
	
}
