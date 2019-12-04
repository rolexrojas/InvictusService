package com.gcs.invictus.domain.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerValidation {

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

    @Override
    public String toString() {
        return "CustomerValidation{" +
                "documentNumber='" + documentNumber + '\'' +
                ", msisdn='" + msisdn + '\'' +
                ", validationResult=" + validationResult +
                '}';
    }
}
