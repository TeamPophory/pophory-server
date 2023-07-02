package com.pophory.pophoryserver.global.exception;


public class PayloadTooLargeException extends RuntimeException {

    public PayloadTooLargeException(String message) {
        super(message);
    }
}
