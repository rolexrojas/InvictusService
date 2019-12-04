package com.gcs.invictus.service.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultKeyValueOperationsSupport.KeyValueBackend;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;

import java.util.HashMap;
import java.util.Map;


@Service
public class VaultService {

    private Logger log = LogManager.getLogger(VaultService.class);


    private VaultTemplate vaultTemplate;

    @Autowired
    public void setVaultTemplate(VaultTemplate vaultTemplate) {
        this.vaultTemplate = vaultTemplate;
    }

    public Map<String, Object> GetVaultSecret(String vaultContext){
        log.info("into getVaultSecret");
        Map<String, Object> sample = new HashMap<>();
        VaultResponse vaultResponse = vaultTemplate.opsForKeyValue("secret/", KeyValueBackend.KV_2).get(vaultContext);
        if(vaultResponse != null && vaultResponse.getData() != null){
            log.info("vaultResponse not null and has getData object");
            sample = vaultResponse.getData();
        }

        return sample;
    }
}
