package com.obs.sampleproject.util;

import com.obs.sampleproject.model.entity.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseUtil {

    public <T> ResponseEntity<Object> generate(String errorCode, T output){
        Response<T> response = new Response<>();
        response.setErrorCode(errorCode);
        response.setOutput(output);
        return ResponseEntity.status(200).body(response);
    }
}
