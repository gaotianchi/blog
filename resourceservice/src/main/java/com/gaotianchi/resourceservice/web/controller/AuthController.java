package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final TokenService tokenService;

    @Autowired
    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

//    @PostMapping("/token/new")
//    public ResponseEntity<TokenResponse> newToken(@RequestBody NewTokenRequest newTokenRequest) {
//        try {
//            TokenResponse tokenResponse = tokenService.getTokenResponse(newTokenRequest.getEmail(), newTokenRequest.getPassword());
//            return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
//        } catch (AuthenticationException ignored) {
//
//        }
//        return null;
//    }
}
