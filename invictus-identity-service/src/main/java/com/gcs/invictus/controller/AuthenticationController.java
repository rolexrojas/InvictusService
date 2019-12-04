package com.gcs.invictus.controller;

import com.gcs.invictus.common.Common;
import com.gcs.invictus.config.ApplicationProperties;
import com.gcs.invictus.domain.dto.AccessRequestDTO;
import com.gcs.invictus.domain.dto.JwtDTO;
import com.gcs.invictus.service.implementation.AuthenticationService;
import com.gcs.invictus.component.JwtManager;
import com.gcs.invictus.service.implementation.VaultService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gcs.invictus.util.Constants;

import java.util.Map;

@RestController
@RequestMapping("/Authenticate")
public class AuthenticationController {

    private Logger log = LogManager.getLogger(AuthenticationController.class);

    private AuthenticationService authenticationService;

    private ApplicationProperties applicationProperties;

    private VaultService vaultService;

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @Autowired
    public void setApplicationProperties(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Autowired
    public void setVaultService(VaultService vaultService) {
        this.vaultService = vaultService;
    }

    @PostMapping(produces = Constants.GCS_DEFAULT_CONTENT_TYPE, consumes = Constants.GCS_DEFAULT_CONTENT_TYPE, headers = "Content-Type="+Constants.GCS_DEFAULT_CONTENT_TYPE)
    public ResponseEntity RequestAccessToken(@RequestBody AccessRequestDTO accessRequestDTO){
        try {
              boolean isValid = authenticationService.validateCredentials(accessRequestDTO);

            if (isValid) {
                final Map<String, Object> invictus = vaultService.GetVaultSecret(applicationProperties.getVaultInvictusContext());
                if(invictus.size() == 0)
                    throw new NoSuchFieldException("No se pudo retornar el token-name: " + applicationProperties.getTicketSecretKey());

                String signSecret = invictus.get(applicationProperties.getTicketSecretKey()).toString();
                final JwtDTO jwtDTO = authenticationService.generateWebToken(accessRequestDTO, signSecret);
                return ResponseEntity.ok(jwtDTO);
            }
        }catch (Exception e){
            log.error(e);
        }

        return ResponseEntity.status(403).build();
    }






}
