package com.myco.users.config;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {


    private static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    private static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");

//    @PostConstruct
//    public void init() {
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//    }
}
