package com.myco.users.exceptions;

public class FileCouldNotUploadException extends RuntimeException {
    public FileCouldNotUploadException(String message) {
        super(message);
    }
}
