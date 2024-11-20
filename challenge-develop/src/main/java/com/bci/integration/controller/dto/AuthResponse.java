package com.bci.integration.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public record AuthResponse(String id,String createdDate,String updatedDate,String lastLogging, String accessToken,Boolean isActive) {

  public AuthResponse(String accessToken) {
    this(null, null, null, null, accessToken, null);
  }
}
