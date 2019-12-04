package com.gcs.invictus.domain.dto;

public class ReservationDTO {
    private String noReserva;
    private String msisdn;

    public ReservationDTO(String noReserva, String msisdn) {
        this.noReserva = noReserva;
        this.msisdn = msisdn;
    }

    public String getNoReserva() {
        return noReserva;
    }

    public void setNoReserva(String noReserva) {
        this.noReserva = noReserva;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }
}
