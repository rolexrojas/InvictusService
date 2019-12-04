package com.gcs.invictus.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Entity {

    @JsonProperty("documentNumber")
    private String documentNumber = "";


    @JsonProperty("msisdn")
    private String msisdn;

    @JsonProperty("validationResult")
    private ValidationResult validationResult;

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public ValidationResult getValidationResult() {
        return validationResult;
    }

    public void setValidationResult(ValidationResult validationResult) {
        this.validationResult = validationResult;
    }
}
