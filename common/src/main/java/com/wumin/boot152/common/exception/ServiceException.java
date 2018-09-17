package com.wumin.boot152.common.exception;

/**
 * 业务异常
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Integer errorCode;

	public Integer getErrorCode() {
		return errorCode;
	}

	public ServiceException(Integer errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Integer errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}
}
