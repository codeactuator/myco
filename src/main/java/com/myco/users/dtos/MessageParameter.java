package com.myco.users.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageParameter {

    private String countryCode;
    private String language;
    private String contactName;
    private String ownerName;
    private String callerNumber;
}
