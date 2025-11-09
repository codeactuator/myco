package com.myco.users.controllers;


import com.myco.users.dtos.CallRequest;
import com.myco.users.dtos.CallResponse;
import com.myco.users.services.CallService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("v1/call")
public class CallController {

    //@Qualifier("twilioCallService")
    @Qualifier("whatsAppMessageService")
    private final CallService callService;

    public CallController(CallService callService) {
        this.callService = callService;
    }

    @PostMapping
    public ResponseEntity<List<CallResponse>> makeCall(@RequestBody CallRequest callRequest){
        List<CallResponse> callResponses = callService.call(callRequest);
        //List<CallResponse> callResponses = new ArrayList<>();
        return ResponseEntity.ok(callResponses);
    }
}
