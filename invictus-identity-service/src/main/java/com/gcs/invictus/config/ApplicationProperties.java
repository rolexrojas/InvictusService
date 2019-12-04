package com.gcs.invictus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {

    @Value("${transaxion.address}")
    private String transaxionAddress;

    @Value("${time.expiration.token.minute}")
    private int timeExpirationToken;

    @Value("${boleta.api.address}")
    private String boletaApiAddress;

    @Value("${txn.push.timeout}")
    private int pushValidationTimeout;

    @Value("${vault.invictus.context.app}")
    private String vaultInvictusContext;

    @Value("${vault.ticket.secret.key}")
    private String ticketSecretKey;

    @Value("${pin.validation.block}")
    private boolean isPinBlockable;

    public String getTransaxionAddress() {
        return transaxionAddress;
    }

    public String getBoletaApiAddress() {
        return boletaApiAddress;
    }

    public int getTimeExpirationToken() {
        return timeExpirationToken;
    }

    public int getPushValidationTimeout() {
        return pushValidationTimeout;
    }

    public void setPushValidationTimeout(int pushValidationTimeout) {
        this.pushValidationTimeout = pushValidationTimeout;
    }

    public String getVaultInvictusContext() {
        return vaultInvictusContext;
    }

    public String getTicketSecretKey() {
        return ticketSecretKey;
    }

    public boolean isPinBlockable() {
        return isPinBlockable;
    }

    public void setPinBlockable(boolean pinBlockable) {
        isPinBlockable = pinBlockable;
    }
}
