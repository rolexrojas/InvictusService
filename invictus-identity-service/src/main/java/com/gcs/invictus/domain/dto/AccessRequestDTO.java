package com.gcs.invictus.domain.dto;

public class AccessRequestDTO {
    private String noReserva;
    private Long msisdn;

    public AccessRequestDTO() {
    }

    public AccessRequestDTO(String noReserva, Long msisdn) {
        this.noReserva = noReserva;
        this.msisdn = msisdn;
    }

    public String getNoReserva() {
        return noReserva;
    }

    public void setNoReserva(String noReserva) {
        this.noReserva = noReserva;
    }

    public Long getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(Long msisdn) {
        this.msisdn = msisdn;
    }
}
