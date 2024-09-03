package com.gaotianchi.authservice.web.controller;

import com.gaotianchi.authservice.service.UserService;
import com.gaotianchi.authservice.web.error.UserExistingException;
import com.gaotianchi.authservice.web.request.NewUserRequest;
import com.gaotianchi.authservice.web.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/new")
    public ResponseEntity<UserResponse> register(@RequestBody NewUserRequest newUserRequest) {
        try {
            UserResponse userResponse = userService.newUser(newUserRequest.getEmail(), newUserRequest.getPassword(), newUserRequest.getRoles());
            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
        } catch (UserExistingException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return null;
    }
    
    @PatchMapping("/users/deregister/{id}")
    public ResponseEntity<Void> deregister(@PathVariable Long id) {
        return null;
    }

    @PatchMapping("/users/lock/{id}")
    public ResponseEntity<Void> lock(@PathVariable Long id) {
        return null;
    }
}
