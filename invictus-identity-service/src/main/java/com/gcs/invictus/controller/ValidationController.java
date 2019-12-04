package com.gcs.invictus.controller;

import com.gcs.invictus.component.JwtManager;
import com.gcs.invictus.config.ApplicationProperties;
import com.gcs.invictus.domain.dto.JwtDTO;
import com.gcs.invictus.domain.dto.ResponseDTO;
import com.gcs.invictus.service.implementation.VaultService;
import com.gcs.invictus.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/Validate")
public class ValidationController {

    private Logger logger = LogManager.getLogger(ValidationController.class);

    private JwtManager jwtManager;

    private ApplicationProperties applicationProperties;

    private VaultService vaultService;

    @Autowired
    public void setJwtManager(JwtManager jwtManager){
        this.jwtManager = jwtManager;
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
    public ResponseEntity ValidateToken(@RequestBody JwtDTO jwt){

        try {
            final Map<String, Object> invictus = vaultService.GetVaultSecret(applicationProperties.getVaultInvictusContext());
            if(invictus.size() == 0) {
                throw new NoSuchFieldException("No se pudo retornar el token-name: " + applicationProperties.getTicketSecretKey());
            }

            String signSecret = invictus.get(applicationProperties.getTicketSecretKey()).toString();
            boolean isValid = jwtManager.isTokenValid(jwt.getJwt(), signSecret);

            if (isValid) {
                ResponseDTO response = new ResponseDTO(Constants.SUCCESS_CODE, Constants.SUCCESS_MSG);
                return ResponseEntity.ok(response);
            }

        }catch (Exception e){
            logger.error(e);
        }

        return ResponseEntity.status(401).build();
    }
}
