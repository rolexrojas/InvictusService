package com.gcs.invictus.service.implementation;

import com.gcs.invictus.common.Common;
import com.gcs.invictus.component.TicketManager;
import com.gcs.invictus.domain.dto.AccessRequestDTO;
import com.gcs.invictus.domain.dto.AuthPin;
import com.gcs.invictus.domain.dto.BasicAuth;
import com.gcs.invictus.domain.dto.JwtDTO;
import com.gcs.invictus.service.IAuthenticationService;
import com.gcs.invictus.component.JwtManager;
import com.gcs.invictus.component.PinManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {

    private Logger log = LogManager.getLogger(AuthenticationService.class);

    private JwtManager jwtManager;

    private PinManager pinManager;

    private TicketManager ticketManager;

    @Autowired
    public void setJwtManager(JwtManager jwtManager) {
        this.jwtManager = jwtManager;
    }

    @Autowired
    public void setPinManager(PinManager pinManager) {
        this.pinManager = pinManager;
    }

    @Autowired
    public void setTicketManager(TicketManager ticketManager) {
        this.ticketManager = ticketManager;
    }

    @Override
    public boolean validateCredentials(AccessRequestDTO accessRequestDTO) {
        log.info("into validateCredentials");
              //Reserve and phone no against ticketApi is the first step
        boolean isValid = ticketManager.verifyReserve(accessRequestDTO.getNoReserva(), accessRequestDTO.getMsisdn());

        if(isValid){

            final AuthPin authPin = new AuthPin(accessRequestDTO.getMsisdn());
            isValid = pinManager.validatePinByPush(authPin);
        }

        return isValid;
    }

    @Override
    public boolean validateCredentials(BasicAuth basicAuth, AccessRequestDTO accessRequestDTO) {
        return false;
    }

    @Override
    public JwtDTO generateWebToken(AccessRequestDTO accessRequestDTO, String signSecret) throws NoSuchFieldException {
        String payload = jwtManager.generateJwtPayload(Common.object2Map(accessRequestDTO));
        String jsonWebToken = jwtManager.generateJsonWebToken(payload, signSecret);
        return jwtManager.jwt(jsonWebToken);
    }


}
