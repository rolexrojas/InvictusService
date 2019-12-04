package com.gcs.invictus.service;

import com.gcs.invictus.domain.dto.AccessRequestDTO;
import com.gcs.invictus.domain.dto.BasicAuth;
import com.gcs.invictus.domain.dto.JwtDTO;
import org.springframework.http.ResponseEntity;

public interface IAuthenticationService {

      boolean validateCredentials(AccessRequestDTO accessRequestDTO);
      boolean validateCredentials(BasicAuth basicAuth, AccessRequestDTO accessRequestDTO);
      JwtDTO generateWebToken(AccessRequestDTO accessRequestDTO, String signSecret) throws NoSuchFieldException;
}
