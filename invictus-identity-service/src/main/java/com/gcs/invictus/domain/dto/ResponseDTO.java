package com.gcs.invictus.domain.dto;

import java.io.Serializable;

public class ResponseDTO implements Serializable {

    private String responseCode;
    private String description;

    public ResponseDTO(String responseCode, String description) {
        this.responseCode = responseCode;
        this.description = description;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
