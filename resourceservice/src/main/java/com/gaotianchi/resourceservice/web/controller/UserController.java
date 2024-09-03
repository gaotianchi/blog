package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.error.EntityAlreadyExistException;
import com.gaotianchi.resourceservice.service.UserService;
import com.gaotianchi.resourceservice.web.request.NewUserRequest;
import com.gaotianchi.resourceservice.web.response.UserResponse;
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

    @PostMapping("/users/new")
    public ResponseEntity<UserResponse> newUser(@RequestBody NewUserRequest newUserRequest) {
        try {
            UserResponse userResponse = userService.newUser(newUserRequest.getPenName(), newUserRequest.getEmail(), newUserRequest.getPassword());
            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
        } catch (EntityAlreadyExistException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }


}
