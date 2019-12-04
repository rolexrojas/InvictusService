package com.gcs.invictus.component;

import com.gcs.invictus.config.ApplicationProperties;
import com.gcs.invictus.domain.dto.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


/**
 * Created by yarielinfante on 11/1/16.
 */
@Component
public class PinManager {


    private ApplicationProperties applicationProperties;

    @Autowired
    public void setApplicationProperties(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    private Logger log = LogManager.getLogger(PinManager.class);

    /*public boolean verifyPin(AuthPin authPin) {
        log.info("Entering verifyPin");
        String uri = applicationProperties.getTransaxionAddress() + "/authenticate-pin";

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Accept", "*");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        HttpEntity<AuthPin> request = new HttpEntity<>(authPin, headers);

        log.info("Exiting verifyPin");
        return restTemplate.postForObject(uri, request, String.class).equals("true");
    }
*/
    public boolean validatePinByPush(AuthPin authPin){

        log.info("Entering validate Pin By Push");
        String uri = applicationProperties.getTransaxionAddress() + "/pushResource/validate-pin";
        boolean response = false;
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Accept", "*/*");

        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        ValidatePinDTO validatePinDTO = new ValidatePinDTO(authPin.getMsisdn().toString(), applicationProperties.isPinBlockable());

        HttpEntity<ValidatePinDTO> request = new HttpEntity<>(validatePinDTO, headers);

        try {
            Context customerValidation = restTemplate.postForObject(uri, request, Context.class);
            if(customerValidation != null && customerValidation.getEntity().getValidationResult() !=null)
                response = customerValidation.getEntity().getValidationResult().equals(ValidationResult.Success);
                log.info("Pin Validation Response: " + response);
            // return the response code here.
        } catch (Exception ex) {
            log.error(ex);
            // Catch the timeout exception
        }

        log.info("Exiting validate Pin By Push");
        return response;
    }


    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        log.info("getClientHttpRequestFactory");
        //int timeout = applicationProperties.getPushValidationTimeout();
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        return clientHttpRequestFactory;
    }

}
