package com.gaotianchi.authorizationservice.web.controller;

import com.gaotianchi.authorizationservice.entity.UserEntity;
import com.gaotianchi.authorizationservice.service.UserDetailsService;
import com.gaotianchi.authorizationservice.web.dto.UserDto;
import com.gaotianchi.authorizationservice.web.error.UserExistingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserDetailsService userDetailsService;

    @Autowired
    public UserController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/users/register")
    public ResponseEntity<UserEntity> register(@RequestBody UserDto userDto) throws UserExistingException {
        if (userDetailsService.userExists(userDto.getEmail())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        UserEntity userEntity = userDetailsService.registerNowUserAccount(userDto);
        return new ResponseEntity<>(userEntity, HttpStatus.CREATED);
    }
}
