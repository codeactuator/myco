package com.myco.users;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;

import java.net.URI;

public class TwilioCallApp {

    public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN = "";
    public static final String fromPhoneNumber = "";
    public static final String toPhoneNumber = "";

    public static void no_main(String args[]){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String twiml = "<Response>" +
                "<Say language=\"pa-IN\" voice=\"Google.hi-IN-Standard-A\">" +
                //"नमस्ते! Samriddhi यह एक परीक्षण कॉल है।" +
                //"হ্যালো সমৃদ্ধি, এটি আপনার জন্য একটি জরুরি কল, আপনার আত্মীয় জরুরিভাবে আপনার সাহায্য চাইছেন, এই নম্বরে যোগাযোগ করুন 8264481868" +
                "ਹੈਲੋ ਸਮਰਿਧੀ, ਇਹ ਤੁਹਾਡੇ ਲਈ ਇੱਕ ਐਮਰਜੈਂਸੀ ਕਾਲ ਹੈ, ਤੁਹਾਡਾ ਰਿਸ਼ਤੇਦਾਰ ਤੁਹਾਡੀ ਮਦਦ ਦੀ ਤੁਰੰਤ ਮੰਗ ਕਰ ਰਿਹਾ ਹੈ, ਇਸ ਨੰਬਰ 'ਤੇ ਸੰਪਰਕ ਕਰੋ 8264481868" +
                "</Say>" +
                "</Response>";

        Call call = Call.creator(
                        new PhoneNumber(toPhoneNumber),   // To number
                        new PhoneNumber(fromPhoneNumber),   // From a Twilio number
                        URI.create("http://twimlets.com/echo?Twiml=" +
                                java.net.URLEncoder.encode(twiml,
                                        java.nio.charset.StandardCharsets.UTF_8)))
                .create();

        System.out.println("Call initiated with SID: " + call.getSid());
    }
}
