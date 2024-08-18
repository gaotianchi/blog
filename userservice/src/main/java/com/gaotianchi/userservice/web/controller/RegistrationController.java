package com.gaotianchi.userservice.web.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    @PostMapping("/users")
    public String registerNewUser(@RequestParam("method") String method, @RequestHeader("Authorization") String accessToken) {
        return method + " " + accessToken;
    }
}
