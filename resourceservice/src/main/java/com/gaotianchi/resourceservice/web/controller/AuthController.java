package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.service.TokenService;
import com.gaotianchi.resourceservice.web.request.NewTokenRequest;
import com.gaotianchi.resourceservice.web.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final TokenService tokenService;

    @Autowired
    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token/new")
    public ResponseEntity<TokenResponse> newToken(@RequestBody NewTokenRequest newTokenRequest) {
        try {
            TokenResponse tokenResponse = tokenService.getTokenResponse(newTokenRequest.getEmail(), newTokenRequest.getPassword());
            return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
        } catch (AuthenticationException ignored) {

        }
        return null;
    }
}
