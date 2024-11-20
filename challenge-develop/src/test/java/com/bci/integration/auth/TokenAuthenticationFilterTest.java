package com.bci.integration.auth;

import com.bci.integration.controller.dto.SignUpRequest;
import com.bci.integration.entity.User;
import com.bci.integration.repository.UserRepository;
import com.bci.integration.security.TokenAuthenticationFilter;
import com.bci.integration.security.TokenProvider;
import com.bci.integration.util.Generic;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TokenAuthenticationFilterTest {
  @Mock
  private MockHttpServletRequest request;
  @Mock
  private MockHttpServletResponse response;
  @Mock
  private MockFilterChain chain;
  @Mock
  private OncePerRequestFilter filter;
  @Mock
  private HttpServlet servlet;
  @Mock
  private UserDetailsService userDetailsService;
  @Mock
  private TokenProvider tokenProvider;
  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private TokenAuthenticationFilter tokenAuthenticationFilter;

  @Test
  void doFilterOnce() throws ServletException, IOException, java.io.IOException {
    String email = "jmp1204@gmail.com";

   // when(userRepository.findByEmail(email)).thenReturn(Optional.of(mapToUser()));

    tokenAuthenticationFilter.doFilter(request,response,chain);
    //verify(userDetailsService, times(1)).loadUserByUsername(anyString());
    //this.filter.doFilter(this.request, this.response, this.chain);

    //assertThat(this.invocations).containsOnly(this.filter);
  }

  private User mapToUser(){

    User user = new User();
    user.setId(UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c" ));
    user.setName("Joel Martinez");
    user.setUsername("jmartinez");
    user.setPassword("12345678jmp");
    user.setEmail("jmp1204@gmail.com");
    user.setCreated(Generic.getLocalTime());
    user.setUpdatedAt(Generic.convertDateToString(LocalDateTime.now()));
    user.setLastLogin(Generic.convertDateToString(LocalDateTime.now()));
    user.setStatus(true);

    return user;
  }
}
