package com.myco.users.services;


import com.myco.users.dtos.CallRequest;
import com.myco.users.dtos.CallResponse;

import java.util.List;

public interface CallService {

    public List<CallResponse> call(CallRequest callRequest);
}
