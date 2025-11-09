package com.myco.users.enums;

public enum ContactType {


    EMERGENCY("Emergency"),
    FAMILY("Family"),
    SELF("Self");

    private final String message;
    ContactType(String value){
        message = value;
    }

    public String toString(){
        return this.message;
    }
}
