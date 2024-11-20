package com.bci.integration.controller;

import com.bci.integration.controller.dto.AuthResponse;
import com.bci.integration.controller.dto.LoginRequest;
import com.bci.integration.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/authenticate")
  public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) {
    return userService.validateUserAndToken(loginRequest);
  }

}
