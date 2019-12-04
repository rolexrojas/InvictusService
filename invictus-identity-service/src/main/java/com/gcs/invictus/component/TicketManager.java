package com.gcs.invictus.component;

import com.gcs.invictus.config.ApplicationProperties;
import com.gcs.invictus.domain.dto.ReservationDTO;
import com.gcs.invictus.domain.dto.TicketResponseDTO;
import com.gcs.invictus.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class TicketManager {

    private Logger log = LogManager.getLogger(TicketManager.class);

    private ApplicationProperties applicationProperties;

    @Autowired
    public void setApplicationProperties(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public boolean verifyReserve(String noReserva, Long msisdn){
        log.info("verifyReserve");
        String uri = applicationProperties.getBoletaApiAddress() + "/ValidateReservation";
        boolean response = false;
        ReservationDTO reservationDTO = new ReservationDTO(noReserva, msisdn.toString());
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Accept", "*/*");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        HttpEntity<ReservationDTO> request = new HttpEntity<>(reservationDTO, headers);
        TicketResponseDTO ticketResponseDTO = restTemplate.postForObject(uri, request, TicketResponseDTO.class);

        if(ticketResponseDTO != null && ticketResponseDTO.getCode().equals(Constants.TICKET_SUCCESS_CODE)){
            response = true;
        }

        return response;
    }
}
