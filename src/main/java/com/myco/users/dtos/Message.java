package com.myco.users.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Message {

    private String countryCode;
    private String languageName;
    private String language;
    private String message;
}
