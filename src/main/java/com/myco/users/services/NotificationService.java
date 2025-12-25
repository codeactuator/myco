package com.myco.users.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Service
public class NotificationService {

    @Value("${twilio.account.sid:}")
    private String accountSid;

    @Value("${twilio.auth.token:}")
    private String authToken;

    @Value("${twilio.phone.number:}")
    private String fromPhoneNumber;

    @PostConstruct
    public void init() {
        if (accountSid != null && !accountSid.isEmpty() && authToken != null && !authToken.isEmpty()) {
            Twilio.init(accountSid, authToken);
        }
    }

    public void sendWhatsAppMessage(String toPhoneNumber, String messageBody, String imageUrl) {
        // Mocking for development
        System.out.println("Mocking WhatsApp Message to: " + toPhoneNumber);
        System.out.println("Body: " + messageBody);
        System.out.println("Image: " + imageUrl);

        /* Real implementation
        if (accountSid == null || accountSid.isEmpty() || authToken == null || authToken.isEmpty()) {
            throw new IllegalStateException("Twilio configuration is missing. Please check application.properties.");
        }
        Message.creator(
                new PhoneNumber("whatsapp:" + toPhoneNumber),
                new PhoneNumber("whatsapp:" + fromPhoneNumber),
                messageBody
        ).setMediaUrl(List.of(URI.create(imageUrl)))
         .create();
         */
    }
}