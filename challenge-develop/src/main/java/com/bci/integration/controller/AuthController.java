package com.bci.integration.controller;


import com.bci.integration.controller.dto.AuthResponse;
import com.bci.integration.controller.dto.LoginRequest;
import com.bci.integration.controller.dto.SignUpRequest;
import com.bci.integration.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user/signup")
    public AuthResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) {

      return (userService.saveAndValidateUserByToken(signUpRequest));
    }




}
