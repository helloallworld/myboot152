package com.wumin.boot152.security.exception;

public class MoreUsersException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private int errorCode;

    public int getErrorCode() {
        return errorCode;
    }

    public MoreUsersException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    public MoreUsersException(String message) {
        super(message);
    }
    public MoreUsersException() {
        super();
    }
    public MoreUsersException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
