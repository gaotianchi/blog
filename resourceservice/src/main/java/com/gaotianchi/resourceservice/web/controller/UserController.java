package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.entity.UserEntity;
import com.gaotianchi.resourceservice.service.UserService;
import com.gaotianchi.resourceservice.web.dto.RegistrationDto;
import com.gaotianchi.resourceservice.web.error.UserAlreadyExistException;
import com.gaotianchi.resourceservice.web.error.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserEntity> createNewUser(@RequestBody RegistrationDto registrationDto) {
        try {
            UserEntity userEntity = userService.createNewUser(registrationDto.getEmail());
            return new ResponseEntity<>(userEntity, HttpStatus.CREATED);
        } catch (UserAlreadyExistException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
