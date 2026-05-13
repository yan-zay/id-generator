package com.tj.id.exception;

public class IdGenerateException extends RuntimeException {

    private final int code;

    public IdGenerateException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
