package com.bci.integration.service.user;


import com.bci.integration.controller.dto.AuthResponse;
import com.bci.integration.controller.dto.LoginRequest;
import com.bci.integration.controller.dto.SignUpRequest;
import com.bci.integration.controller.dto.SignUpRequest.PhoneRequest;
import com.bci.integration.entity.Phone;
import com.bci.integration.entity.User;
import com.bci.integration.exception.BadRequestException;
import com.bci.integration.exception.DuplicatedUserInfoException;
import com.bci.integration.exception.ServerErrorException;
import com.bci.integration.exception.UserNotFoundException;
import com.bci.integration.repository.PhoneRepository;
import com.bci.integration.repository.UserRepository;
import com.bci.integration.security.TokenProvider;
import com.bci.integration.security.WebSecurityConfig;
import com.bci.integration.util.Generic;
import com.bci.integration.util.PasswordValidator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordValidator passwordValidator;
    private final PhoneRepository phoneRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository,
        TokenProvider tokenProvider, PasswordValidator passwordValidator,
        PhoneRepository phoneRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.passwordValidator = passwordValidator;
        this.phoneRepository = phoneRepository;
    }
    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public AuthResponse validateUserAndToken(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new BadRequestException("Invalid email or password."));
        user.setLastLogin(Generic.convertDateToString(LocalDateTime.now()));
        user.setUpdatedAt(Generic.convertDateToString(LocalDateTime.now()));

        try{
            userRepository.saveAndFlush(user);
        }catch (Exception e){
            throw new ServerErrorException(passwordValidator.getErrorMessage());
        }

        return new AuthResponse(user.getId().toString(), user.getCreated(), user.getUpdatedAt(), user.getLastLogin(), user.getToken(), null);

    }

    @Override
    public AuthResponse saveAndValidateUserByToken(SignUpRequest signUpRequest){
        User user = mapSignUpRequestToUser(signUpRequest);
        String token;
        isUserInDbByEmailAndUserName(signUpRequest.getUserName(),signUpRequest.getEmail());
        token = tokenProvider.generate(signUpRequest);
        user.setToken(token);
        try{
            userRepository.save(user);
        }catch (Exception e){
            throw new ServerErrorException(passwordValidator.getErrorMessage());
        }
        return new AuthResponse(user.getId().toString(),user.getCreated(),null,null,token,user.getStatus());
    }

    private User mapSignUpRequestToUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(signUpRequest.getUserName());

        if(Generic.isValid(signUpRequest.getPassword())){
            throw new ServerErrorException(passwordValidator.getErrorMessage());
        }

        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setRole(WebSecurityConfig.USER);
        user.setCreated(Generic.getLocalTime());
        user.setStatus(true);
        List<Phone> phones = mapSignUpRequestToPhone(signUpRequest.getPhones());

        try {
            user.setPhones(phones);
            phoneRepository.saveAll(phones);
        }catch (Exception e){
            throw  new ServerErrorException(e.getMessage());
        }

        return user;
    }
    private List<Phone> mapSignUpRequestToPhone(List<PhoneRequest> phoneRequests){
        List<Phone> phones = new ArrayList<>();
        if(Optional.ofNullable(phoneRequests).isEmpty()){
            throw new UserNotFoundException("There are not phones");
        }
        phoneRequests.forEach(phoneRequest -> {
            Phone phone = new Phone();
            phone.setNumber(phoneRequest.getNumber());
            phone.setCityCode(phoneRequest.getCityCode());
            phone.setCountryCode(phoneRequest.getCountryCode());
            phones.add(phone);
        });
        return phones;
    }

    private void isUserInDbByEmailAndUserName(String userName,String email){
        if (userRepository.existsByUsername(userName)) {
            throw new DuplicatedUserInfoException(String.format("Username %s already been used", userName));
        }
        if (userRepository.existsByEmail(email)) {
            throw new DuplicatedUserInfoException(String.format("Email %s already been used", email));
        }
    }
}
