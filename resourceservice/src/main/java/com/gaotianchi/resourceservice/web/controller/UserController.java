package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.error.EntityAlreadyExistException;
import com.gaotianchi.resourceservice.error.EntityNotFoundException;
import com.gaotianchi.resourceservice.service.UserService;
import com.gaotianchi.resourceservice.web.request.NewUserRequest;
import com.gaotianchi.resourceservice.web.request.UpdateUserInfoRequest;
import com.gaotianchi.resourceservice.web.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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


    @PatchMapping("/users/set-avatar/{imageId}")
    public ResponseEntity<UserResponse> setAvtar(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long imageId) {
        try {
            UserResponse userResponse = userService.setAvatar(userDetails.getUsername(), imageId);
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/users/update-info")
    public ResponseEntity<UserResponse> updateInfo(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UpdateUserInfoRequest updateUserInfoRequest) {
        try {
            UserResponse userResponse = userService.updateUserInfo(userDetails.getUsername(), updateUserInfoRequest.getPenName());
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
