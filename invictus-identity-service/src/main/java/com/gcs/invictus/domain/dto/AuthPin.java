package com.gcs.invictus.domain.dto;

public class AuthPin {

    private Long msisdn;

    public AuthPin(Long msisdn) {
        this.msisdn = msisdn;
    }

    public AuthPin() {
    }

    public Long getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(Long msisdn) {
        this.msisdn = msisdn;
    }
}
