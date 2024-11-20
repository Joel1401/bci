package com.bci.integration.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bci.integration.controller.dto.AuthResponse;
import com.bci.integration.controller.dto.LoginRequest;
import com.bci.integration.controller.dto.SignUpRequest;
import com.bci.integration.entity.Phone;
import com.bci.integration.entity.User;
import com.bci.integration.repository.PhoneRepository;
import com.bci.integration.repository.UserRepository;
import com.bci.integration.security.TokenProvider;
import com.bci.integration.service.user.UserServiceImpl;
import com.bci.integration.util.Generic;
import com.bci.integration.util.PasswordValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AuthenticationTest {

  @Mock
  private  PasswordEncoder passwordEncoder;
  @Mock
  private  UserRepository userRepository;
  @Mock
  private  TokenProvider tokenProvider;
  @Mock
  private  PasswordValidator passwordValidator;
  @Mock
  private  PhoneRepository phoneRepository;

  @InjectMocks
  private UserServiceImpl userService;



  @DisplayName("JUnit test signup  happy path  ")
  @Test
  public void signup_happy_path() throws Exception {
    String jsonInput = "{\n"
        + "    \"name\": \"Juan Rodriguez\",\n"
        + "    \"userName\":\"jrod\",\n"
        + "    \"email\": \"juan@rodriguez.org\",\n"
        + "    \"password\": \"password123\",\n"
        + "    \"phones\": [\n"
        + "        {\"number\": \"1234567\",\n"
        + "    \"cityCode\": \"1\",\n"
        + "    \"countryCode\": \"57\"}\n"
        + "    ]\n"
        + "\n"
        + "}";

    SignUpRequest signUpRequest = generateRequest(jsonInput);
    List<Phone> phones = new ArrayList<>();
    signUpRequest.getPhones().forEach(phoneRequest -> {
      Phone phone = new Phone();
      phone.setNumber(phoneRequest.getNumber());
      phone.setCityCode(phoneRequest.getCityCode());
      phone.setCountryCode(phoneRequest.getCountryCode());
      phones.add(phone);
    });

    User user = mapToUser(signUpRequest);
    user.setPhones(phones);

    when(tokenProvider.generate(signUpRequest)).thenReturn("$2a$10$yWz5iJmc60WCPouLx4lyF.cKM12bNwsuutS59oT4LWTFY8KoHgq0K");

    when(passwordEncoder.encode(signUpRequest.getPassword())).thenReturn(
        "$2a$10$yWz5iJmc60WCPouLx4lyF.cKM12bNwsuutS59oT4LWTFY8KoHgq0K");
    AuthResponse authResponse = userService.saveAndValidateUserByToken(signUpRequest);

    verify(phoneRepository, times(1)).saveAll(phones);
    verify(userRepository, times(1)).save(Mockito.any(User.class));
    assertThat(authResponse).isNotNull();


  }

  @DisplayName("JUnit test sigin  happy path  ")
  @Test
  public void signin_happy_path() throws Exception {

    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsername("jrod");
    loginRequest.setEmail("juan@rodriguez.org");
    loginRequest.setPassword("password123");

    when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(mapToUser()));

    AuthResponse response = userService.validateUserAndToken(loginRequest);
    verify(userRepository, times(1)).saveAndFlush(Mockito.any(User.class));
    assertThat(response).isNotNull();

  }

  private User mapToUser(SignUpRequest signUpRequest){

    User user = new User();
    user.setId(UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c" ));
    user.setName(signUpRequest.getName());
    user.setUsername(signUpRequest.getUserName());
    user.setPassword(signUpRequest.getPassword());
    user.setEmail(signUpRequest.getEmail());
    user.setStatus(true);
    user.setToken("$2a$10$yWz5iJmc60WCPouLx4lyF.cKM12bNwsuutS59oT4LWTFY8KoHgq0K");

    return user;
  }
  private SignUpRequest generateRequest(String jsonInput) throws JsonProcessingException {
    return new ObjectMapper().readValue(jsonInput, SignUpRequest.class);
  }

  private User mapToUser(){

    User user = new User();
    user.setId(UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c" ));
    user.setName("Joel Martinez");
    user.setUsername("jmartinez");
    user.setPassword("12345678jlo");
    user.setEmail("jmartinez@gmail.com");
    user.setCreated(Generic.getLocalTime());
    user.setUpdatedAt(Generic.convertDateToString(LocalDateTime.now()));
    user.setLastLogin(Generic.convertDateToString(LocalDateTime.now()));
    user.setStatus(true);

    return user;
  }

}
