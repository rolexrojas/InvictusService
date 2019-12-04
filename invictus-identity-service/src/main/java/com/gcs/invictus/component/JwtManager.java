package com.gcs.invictus.component;

import com.gcs.invictus.common.Common;
import com.gcs.invictus.config.ApplicationProperties;
import com.gcs.invictus.domain.dto.AccessRequestDTO;
import com.gcs.invictus.service.implementation.VaultService;
import com.gcs.invictus.util.Constants;
import io.jsonwebtoken.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;
import org.springframework.vault.support.VaultTransitKey;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.stream.Collectors;
import com.gcs.invictus.domain.dto.JwtDTO;


@Component
public class JwtManager {

    private Logger log = LogManager.getLogger(JwtManager.class);

    private ApplicationProperties applicationProperties;

    private VaultService vaultService;

    @Autowired
    public void setApplicationProperties(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Autowired
    public void setVaultService(VaultService vaultService) {
        this.vaultService = vaultService;
    }

    public String generateJwtPayload(Map<String, Object> payloadProperties) {
        String payload = payloadProperties.entrySet().stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining(","));

        return payload;
    }


    public String generateJsonWebToken(String payload, String signSecret) throws NoSuchFieldException {
        log.info("into generateJsonWebToken");
        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.forName(Constants.GCS_TOKEN_SIGNATURE_ALGORITHM);
        Calendar calendar = GregorianCalendar.getInstance();
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(signSecret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(Common.generateUUID())
                .setIssuedAt(calendar.getTime())
                .setSubject(payload)
                .setAudience(Constants.TICKET_PORTAL)
                .setIssuer(Constants.TOKEN_ISSUER)
                .signWith(SignatureAlgorithm.HS512, signingKey);

        calendar.add(Calendar.MINUTE, applicationProperties.getTimeExpirationToken());
        builder.setExpiration(calendar.getTime());
        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }


    private Claims decodeJsonWebToken(String jwt, String signSecret) throws NoSuchFieldException {

        log.info("into decodeJsonWebToken");
        //This line will throw an exception if it is not a signed JWS (as expected)
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(signSecret))
                .parseClaimsJws(jwt).getBody();
    }

    public boolean isTokenValid(String jwt, String signSecret){
        log.info("into isTokenValid");
        boolean response = false;
         try {
             Claims claim = decodeJsonWebToken(jwt, signSecret);
             response = validateClaimsContent(claim);

         }catch (Exception e){
             log.info(e);
         }

         return response;
    }

    public JwtDTO jwt(String token) {
        return new JwtDTO(token);
    }

    private boolean validateClaimsContent(Claims claim){
        log.info("into validateClaimsContent");
        boolean response = false;
        if((claim.getIssuer().equals(Constants.TOKEN_ISSUER))
                  || (!claim.getSubject().isEmpty())){
            response = true;
        }
        return response;
    }

    /*public String generateObjectFromPayLoadBasedOnAudience(String Audience, String payload){
        switch (Audience) {
            case TICKET_PORTAL:
                AccessRequestDTO accessRequestDTO = new AccessRequestDTO();
                break;
                default:
                    break;
                    return "";
        }
        return "";
    }   */

}
