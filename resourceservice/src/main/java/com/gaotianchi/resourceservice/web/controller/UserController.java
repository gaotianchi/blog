package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.error.UserAlreadyExistException;
import com.gaotianchi.resourceservice.persistence.entity.UserEntity;
import com.gaotianchi.resourceservice.service.UserService;
import com.gaotianchi.resourceservice.web.request.RegistrationDto;
import com.gaotianchi.resourceservice.web.response.UserOtd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserOtd> createNewUser(@RequestBody RegistrationDto registrationDto) {
        try {
            UserEntity userEntity = userService.createNewUser(registrationDto.getEmail());
            return new ResponseEntity<>(new UserOtd(userEntity), HttpStatus.CREATED);
        } catch (UserAlreadyExistException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
