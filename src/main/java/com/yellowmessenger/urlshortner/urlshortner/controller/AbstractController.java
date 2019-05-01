package com.yellowmessenger.urlshortner.urlshortner.controller;

import com.yellowmessenger.urlshortner.urlshortner.dto.UIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author karthikjoshi

 * Method to print output in JSON response with specific key-value pair.
 * data -> output of the api
 * code -> Application specific code
 * message -> Short description of the api
 * status -> OK for success,FAIL for bad request and FAULT for Internal server error
 */
public class AbstractController {

    protected <T> ResponseEntity<UIResponse<T>> buildResponse(final T t, String message)
    {
        final UIResponse<T> uiResponse = new UIResponse<>(t);
        uiResponse.setCode(1200);
        uiResponse.setMessage(message);
        uiResponse.setStatus("OK");
        return new ResponseEntity<>(uiResponse,HttpStatus.OK);
    }
}
