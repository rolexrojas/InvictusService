package com.gcs.invictus.domain.dto;

import java.io.Serializable;

public class JwtDTO implements Serializable {

    private String jwt;

    public JwtDTO(){}

    public JwtDTO(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
