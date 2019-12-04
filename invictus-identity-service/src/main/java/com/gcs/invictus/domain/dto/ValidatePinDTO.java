package com.gcs.invictus.domain.dto;

public class ValidatePinDTO {

    private String msisdn;
    private boolean blockable;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public boolean isBlockable() {
        return blockable;
    }

    public void setBlockable(boolean blockable) {
        this.blockable = blockable;
    }

    public ValidatePinDTO(String msisdn, boolean blockable) {
        this.msisdn = msisdn;
        this.blockable = blockable;
    }
}
