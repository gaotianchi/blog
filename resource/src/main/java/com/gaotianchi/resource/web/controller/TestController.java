package com.gaotianchi.resource.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {
    @GetMapping("/resource/article")
    public String article(@AuthenticationPrincipal Jwt jwt) {
        return jwt.getSubject();
    }
}
