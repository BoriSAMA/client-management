package com.seek.client.adapter.in.rest.controller;

import com.seek.client.security.dto.JwtResponse;
import com.seek.client.security.dto.LoginRequest;
import com.seek.client.security.jwt.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthenticationControllerTest {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private UserDetailsService userDetailsService;
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        authenticationManager = mock(AuthenticationManager.class);
        jwtUtil = mock(JwtUtil.class);
        userDetailsService = mock(UserDetailsService.class);
        authenticationController = new AuthenticationController(authenticationManager, jwtUtil, userDetailsService);
    }

    @Test
    void shouldPassLogin() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("testuser", "password");
        UserDetails userDetails = User.withUsername("testuser").password("password").roles("USER").build();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken("testuser", "password");

        when(authenticationManager.authenticate(authToken)).thenReturn(mock(Authentication.class));
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);
        when(jwtUtil.generateToken("testuser")).thenReturn("jwt-token");

        // Act
        ResponseEntity<JwtResponse> response = authenticationController.login(loginRequest);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals("jwt-token", response.getBody().getToken());
    }

    @Test
    void shouldFailLoginByWrongCredentials() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("invalid", "wrong");

        doThrow(new BadCredentialsException("Invalid credentials"))
                .when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Act and Assert
        assertThrows(BadCredentialsException.class, () -> authenticationController.login(loginRequest));
    }
}

