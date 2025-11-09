package com.myco.users.exceptions;

public class PostNotFoundException extends RuntimeException{

    public PostNotFoundException(String s) {
        super(s);
    }
}
