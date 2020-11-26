package com.sk.jdp.common.core.exception;

public class ApiErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/*
	 * 에러 코드
	 */
	private final String rspCode;
	/*
	 * 에러 메시지
	 */
	private final String rspMessage;
	
	public ApiErrorException(String rspCode) {
		super();
		this.rspCode = rspCode;
		this.rspMessage = "";
	}

	public ApiErrorException(String rspCode, Throwable ex) {
		super(ex);
		this.rspCode = rspCode;
		this.rspMessage = "";
	}
	
	public ApiErrorException(String rspCode, String rspMessage) {
		super(rspMessage);
		this.rspCode = rspCode;
		this.rspMessage = rspMessage;
	}

	public ApiErrorException(String rspCode, String rspMessage, Throwable ex) {
		super(rspMessage, ex);
		this.rspCode = rspCode;
		this.rspMessage = rspMessage;
	}

	/**
	 * @return the rspCode
	 */
	public String getRspCode() {
		return rspCode;
	}

	/**
	 * @return the rspMessage
	 */
	public String getRspMessage() {
		return rspMessage;
	}
	
}
