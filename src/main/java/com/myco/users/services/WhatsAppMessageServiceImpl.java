package com.myco.users.services;

import com.myco.users.dtos.CallRequest;
import com.myco.users.dtos.CallResponse;
import com.myco.users.dtos.MessageParameter;
import com.myco.users.entities.AppUser;
import com.myco.users.entities.Contact;
import com.myco.users.utils.MessageUtil;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Qualifier("whatsAppMessageService")
@Primary
public class WhatsAppMessageServiceImpl implements CallService{


    private static final String TWILIO_FROM_PHONE = System.getenv("TWILIO_FROM_PHONE");
    private static final String TWILIO_TO_PHONE = System.getenv("TWILIO_TO_PHONE");


    private final ContactService contactService;
    private final AppUserService appUserService;
    private final MessageUtil messageUtil;

    public WhatsAppMessageServiceImpl(ContactService contactService, AppUserService appUserService, MessageUtil messageUtil) {
        this.contactService = contactService;
        this.appUserService = appUserService;
        this.messageUtil = messageUtil;
    }

    @Override
    public List<CallResponse> call(CallRequest callRequest) {
        List<CallResponse> callResponses = new ArrayList<>();
        List<Contact> contacts = contactService.findAllByUserId(callRequest.getToUserId());
        AppUser owner = appUserService.find(callRequest.getToUserId());
        AppUser caller = appUserService.find(callRequest.getFromUserId());


        for (Contact contact : contacts) {
            callRequest.setCaller(caller);
            callRequest.setOwner(owner);
            callRequest.setContact(contact);

            String messageBody = createWhatsappMessages(callRequest);
            Message message = Message.creator(
                    new PhoneNumber("whatsapp:" + TWILIO_TO_PHONE),
                    new PhoneNumber("whatsapp:+14155238886"),
                    messageBody
            ).create();

            CallResponse callResponse = createResponse(message, contact);
            callResponses.add(callResponse);
        }
        return callResponses;
    }


    private CallResponse createResponse(Message message, Contact contact){
        CallResponse callResponse = new CallResponse();
        callResponse.setRequestId(message.getSid());
        callResponse.setInfo(contact.getRelation());
        log.info("Message SID: " + message.getSid());
        return callResponse;
    }

    private String createWhatsappMessages(CallRequest callRequest){
        MessageParameter messageParameter = new MessageParameter(
                "IN",
                "pa-IN",
                callRequest.getContact().getContactName(),
                callRequest.getOwner().getMobileNumber(),
                callRequest.getCaller().getMobileNumber()
        );
        String message = messageUtil.prepareMessage(messageParameter);
        return message;
    }

}
