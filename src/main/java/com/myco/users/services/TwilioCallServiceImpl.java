package com.myco.users.services;

import com.myco.users.dtos.CallRequest;
import com.myco.users.dtos.CallResponse;
import com.myco.users.dtos.MessageParameter;
import com.myco.users.entities.AppUser;
import com.myco.users.entities.Contact;
import com.myco.users.utils.MessageUtil;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Qualifier("twilioCallService")
public class TwilioCallServiceImpl implements CallService{

    private static final String TWILIO_CALL_URL = "https://twimlets.com/message?Message%5B0%5D=";
    private static final String TWILIO_FROM_PHONE = System.getenv("TWILIO_FROM_PHONE");
    private static final String TWILIO_TO_PHONE = System.getenv("TWILIO_TO_PHONE");

    private final ContactService contactService;
    private final AppUserService appUserService;
    private final MessageUtil messageUtil;

    public TwilioCallServiceImpl(ContactService contactService, AppUserService appUserService, MessageUtil messageUtil) {
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

            String message = createCallMessages(callRequest);
            System.out.println("Message: " + message);
            String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);
            String twimlUrl = TWILIO_CALL_URL + encodedMessage;
            Call call = Call.creator(
                    new PhoneNumber(createPhoneNumber(contact)), // To number
                    new PhoneNumber(TWILIO_FROM_PHONE), // From your Twilio number
                    URI.create(twimlUrl)
            ).create();

            CallResponse callResponse = createResponse(call, contact);
            callResponses.add(callResponse);
        }
        return callResponses;
    }

    private CallResponse createResponse(Call call, Contact contact){
        CallResponse callResponse = new CallResponse();
        callResponse.setRequestId(call.getSid());
        callResponse.setInfo(contact.getRelation());
        log.info("Call SID: " + call.getSid());
        return callResponse;
    }

    private String createCallMessages(CallRequest callRequest){
        MessageParameter messageParameter = new MessageParameter(
                "IN",
                "hi-IN",
                callRequest.getContact().getContactName(),
                callRequest.getOwner().getMobileNumber(),
                callRequest.getCaller().getMobileNumber()
        );
        String message = messageUtil.prepareMessage(messageParameter);
        return message;
    }

    private int getCountryISDCode(String countryCode){
        return 91;
    }

    private String createPhoneNumber(Contact contact){
//        return "+"
//                + getCountryISDCode("IN")
//                + contact.getContactNumber();

        return TWILIO_TO_PHONE;
    }
}
