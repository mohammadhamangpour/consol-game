package com.game.consolegame.exception;

import org.springframework.http.HttpStatus;

public class GameException extends RuntimeException{
    private int errorCode;
    private HttpStatus status = HttpStatus.BAD_REQUEST;
    private String details = "";

    public GameException(int errorCode) {
        this.errorCode = errorCode;
    }

    public GameException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public GameException(String message, String details, int errorCode) {
        this(message, errorCode);
        this.details = details;
    }

    public GameException(String message, String details, int errorCode, HttpStatus status){
        this(message, details, errorCode);
        this.details = details;
    }

    public GameException(String message, int errorCode, HttpStatus status) {
        this(message, errorCode);
        this.status = status;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getDetails() {
        return details;
    }
}
