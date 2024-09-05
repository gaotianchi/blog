package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.service.TokenService;
import com.gaotianchi.resourceservice.web.request.NewTokenRequest;
import com.gaotianchi.resourceservice.web.response.APIResponse;
import com.gaotianchi.resourceservice.web.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    private final TokenService tokenService;

    @Autowired
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token/new")
    public APIResponse<TokenResponse> newToken(@RequestBody NewTokenRequest newTokenRequest) {
        TokenResponse tokenResponse = tokenService.newToken(newTokenRequest.getEmail(), newTokenRequest.getPassword());
        return APIResponse.success(tokenResponse);
    }
}
