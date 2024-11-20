package com.bci.integration.service.user;


import com.bci.integration.controller.dto.AuthResponse;
import com.bci.integration.controller.dto.LoginRequest;
import com.bci.integration.controller.dto.SignUpRequest;
import com.bci.integration.entity.User;

public interface UserService {

    void deleteUser(User user);

    AuthResponse validateUserAndToken(LoginRequest loginRequest);

    AuthResponse saveAndValidateUserByToken(SignUpRequest signUpRequest);
}
