package com.myco.users.utils;


import com.myco.users.dtos.Message;
import com.myco.users.dtos.MessageParameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MessageUtil {

    private static final String TWILIO_MESSAGE_TEMPLATE = "<Response>" +
            "<Say language=\"pa-IN\" voice=\"Google.hi-IN-Standard-A\">" +
            "${content}" +
            "</Say>" +
            "</Response>";

    private Map<String, List<Message>> worldLanguageMap;

    public MessageUtil() {
        worldLanguageMap = new HashMap<>();

        List<Message> messages = new ArrayList<>();
        messages.add(new Message("IN", "English", "en-IN",
                "Hello ${contactName}, this is an emergency call for you, your relative ${ownerName} is seeking for your help urgently, contact on this number ${callerNumber}"));
        messages.add(new Message("IN", "Hindi","hi-IN",
                "हेलो ${contactName}, यह आपके लिए एक आपातकालीन कॉल है, आपका रिश्तेदार ${ownerName} आपसे तत्काल मदद मांग रहा है, इस नंबर पर संपर्क करें ${callerNumber}"));
        messages.add(new Message("IN", "Gujarati","gu-IN",
                "હેલો ${contactName}, આ તમારા માટે એક ઇમરજન્સી કોલ છે, તમારા સંબંધી ${ownerName} તાકીદે તમારી મદદ માંગે છે, આ નંબર પર સંપર્ક કરો ${callerNumber}"));
        messages.add(new Message("IN", "Kannada","kn-IN",
                "ನಮಸ್ಕಾರ ${contactName}, ಇದು ನಿಮಗಾಗಿ ತುರ್ತು ಕರೆಯಾಗಿದೆ, ನಿಮ್ಮ ಸಂಬಂಧಿ ${ownerName} ನಿಮ್ಮ ಸಹಾಯವನ್ನು ತುರ್ತಾಗಿ ಬಯಸುತ್ತಿದ್ದಾರೆ, ಈ ಸಂಖ್ಯೆಗೆ ಸಂಪರ್ಕಿಸಿ ${callerNumber}"));
        messages.add(new Message("IN", "Malayalam","ml-IN",
                "ഹലോ ${contactName}, ഇത് നിങ്ങൾക്കുള്ള ഒരു അടിയന്തര കോളാണ്, നിങ്ങളുടെ ബന്ധു ${ownerName} അടിയന്തിരമായി നിങ്ങളുടെ സഹായം തേടുന്നു, ഈ നമ്പറിൽ ബന്ധപ്പെടുക ${callerNumber}"));
        messages.add(new Message("IN", "Marathi","mr-IN",
                "हॅलो ${contactName}, हा तुमच्यासाठी इमर्जन्सी कॉल आहे, तुमचा नातेवाईक ${ownerName} तातडीची मदत घेत आहे, या नंबरवर संपर्क करा ${callerNumber}"));
        messages.add(new Message("IN", "Bengali","bn-IN",
                "হ্যালো ${contactName}, এটি আপনার জন্য একটি জরুরি কল, আপনার আত্মীয় ${ownerName} জরুরীভাবে আপনার সাহায্য চাইছেন, এই নম্বরে যোগাযোগ করুন ${callerNumber}"));
        messages.add(new Message("IN", "Tamil","ta-IN",
                "வணக்கம் ${contactName}, இது உங்களுக்கான அவசர அழைப்பு, உங்கள் உறவினர் ${ownerName} உங்கள் உதவியை அவசரமாக நாடுகிறார், இந்த எண்ணில் தொடர்பு கொள்ளவும் ${callerNumber}"));
        messages.add(new Message("IN", "Telugu","te-IN",
                "హలో ${contactName}, ఇది మీ కోసం అత్యవసర కాల్, మీ బంధువు ${ownerName} అత్యవసరంగా మీ సహాయం కోరుతున్నారు, ఈ నంబర్\u200Cలో సంప్రదించండి ${callerNumber}"));
        messages.add(new Message("IN", "Punjabi","pa-IN",
                "ਹੈਲੋ ${contactName}, ਇਹ ਤੁਹਾਡੇ ਲਈ ਇੱਕ ਐਮਰਜੈਂਸੀ ਕਾਲ ਹੈ, ਤੁਹਾਡਾ ਰਿਸ਼ਤੇਦਾਰ ${ownerName} ਤੁਹਾਡੀ ਮਦਦ ਦੀ ਤੁਰੰਤ ਮੰਗ ਕਰ ਰਿਹਾ ਹੈ, ਇਸ ਨੰਬਰ 'ਤੇ ਸੰਪਰਕ ਕਰੋ ${callerNumber}"));


        messages.add(new Message("FR", "French","fr-FR",
                "Bonjour ${contactName}, ceci est un appel d'urgence pour vous, votre parent ${ownerName} recherche votre aide de toute urgence, contactez ce numéro ${callerNumber}"));

        messages.add(new Message("RO", "Russian","ru-RU",
                "Здравствуйте, ${contactName}, это экстренный звонок для вас, ваш родственник ${ownerName} срочно нуждается в вашей помощи, позвоните по этому номеру ${callerNumber}."));

        worldLanguageMap = messages.stream()
                .collect(Collectors.groupingBy(Message::getCountryCode));
    }

    /**
     * Returns the messages for each language of given country.
     * @param countryCode
     * @return
     */
    private Map<String, Message> getMessages(String countryCode){
        Map<String, Message> messageMap = worldLanguageMap.get(countryCode)
                .stream()
                .collect(Collectors.toMap(message -> message.getLanguage(), message -> message));
        return messageMap;
    }

    /**
     * Returns the message for desired language of given country.
     * @param countryCode
     * @param language
     * @return
     */
    public Message getMessage(String countryCode, String language){
        Message message = getMessages(countryCode).get(language);
        return message;
    }

    /**
     * Returns the list of country which languages are supported.
     * @return
     */
    public Set<String> getCountries(){
        return worldLanguageMap.keySet();
    }

    /**
     * Returns the list of language of given country.
     * @param countryCode
     * @return
     */
    public Set<String> getLanguages(String countryCode){
        return getMessages(countryCode).keySet();
    }

    /**
     * Prepare the final message to be supplied to twilio
     * which we want to play for a customer with their language
     * and caller contact number. This can be enhance whenever
     * there is requirement for change in message.
     * @param parameter
     * @return
     */
    public String prepareMessage(MessageParameter parameter){
        Message message = getMessage(parameter.getCountryCode(), parameter.getLanguage());
        String inlineContent = message.getMessage()
                .replace("${contactName}", parameter.getContactName())
                .replace("${ownerName}", parameter.getOwnerName())
                .replace("${contactNumber}", parameter.getCallerNumber());

        String twilioMessage = TWILIO_MESSAGE_TEMPLATE.replace("${content}", inlineContent);
        return twilioMessage;
    }
}
