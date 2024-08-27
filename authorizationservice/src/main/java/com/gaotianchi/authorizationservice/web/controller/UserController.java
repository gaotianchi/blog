package com.gaotianchi.authorizationservice.web.controller;

import com.gaotianchi.authorizationservice.entity.UserEntity;
import com.gaotianchi.authorizationservice.service.UserDetailsService;
import com.gaotianchi.authorizationservice.web.dto.UserDto;
import com.gaotianchi.authorizationservice.web.error.UserExistingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserDetailsService userDetailsService;

    @Autowired
    public UserController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserEntity> register(@RequestBody UserDto userDto) throws UserExistingException {
        if (userDetailsService.emailExists(userDto.getEmail())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        UserEntity userEntity = userDetailsService.registerNowUserAccount(userDto);
        return new ResponseEntity<>(userEntity, HttpStatus.CREATED);
    }
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        try {
            boolean deleted = userDetailsService.deleteUserById(userId);
            if (deleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
