package com.yellowmessenger.urlshortner.urlshortner.dto;

/**
 * @author karthikjoshi
 * POJO class
 */
public class RateInput {

    public String clientId;
    public long timeOfHit;

    public RateInput(String clientId, long timeOfHit) {
        this.clientId = clientId;
        this.timeOfHit = timeOfHit;
    }
}
