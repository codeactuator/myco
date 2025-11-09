package com.myco.users.exceptions;

public class InvalidOTPException extends RuntimeException {

    private String message;

    public InvalidOTPException(String invalidOtpProvided) {
        super(invalidOtpProvided);
        this.message = invalidOtpProvided;
    }
}
