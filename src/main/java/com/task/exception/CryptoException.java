package com.task.exception;

import com.task.service.CryptoInfoService;

/**
 * The is custom Exception class , to handle error gracefully with error message and error code
 * it extends @{@link RuntimeException}
 */
public class CryptoException extends RuntimeException {


    /**
     * A int value as errorCode which hold ErrorCode for various scenario.
     * ex: 404,501 etc..
     */
    private int errorCode;

    /**
     * constructor overlaod to set errorMessage and errorCode
     * @param message
     * @param errorCode
     */
    public CryptoException(String message,int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
