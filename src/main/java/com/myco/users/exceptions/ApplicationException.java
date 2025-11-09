package com.myco.users.exceptions;

public class ApplicationException extends RuntimeException{

    ApplicationException(String message){
        super(message);
    }
}
