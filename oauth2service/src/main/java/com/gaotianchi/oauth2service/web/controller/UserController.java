package com.gaotianchi.oauth2service.web.controller;

import com.gaotianchi.oauth2service.entity.UserEntity;
import com.gaotianchi.oauth2service.service.UserDetailsService;
import com.gaotianchi.oauth2service.web.dto.EmailUpdatedMessage;
import com.gaotianchi.oauth2service.web.dto.UserDto;
import com.gaotianchi.oauth2service.web.error.EmailAlreadyExistsException;
import com.gaotianchi.oauth2service.web.error.UserExistingException;
import com.gaotianchi.oauth2service.web.error.UserNotFoundException;
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

    @PostMapping("/users/register")
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
    @PatchMapping("/users/{userId}/email")
    public ResponseEntity<EmailUpdatedMessage> updateUserEmail(
            @PathVariable Long userId,
            @RequestBody String newEmail) {
        try {
            EmailUpdatedMessage emailUpdatedMessage = userDetailsService.updateUserEmail(userId, newEmail);
            return new ResponseEntity<>(emailUpdatedMessage, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EmailAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/users/{userId}/password")
    public ResponseEntity<Void> resetPassword(
            @RequestBody String newPassword,
            @PathVariable Long userId) {
        try {
            userDetailsService.resetPassword(userId, newPassword);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/users{userId}/deregister")
    public ResponseEntity<Void> deregisterAccount(@PathVariable Long userId) {
        try {
            userDetailsService.deregister(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/users/{userId}/lock")
    public ResponseEntity<Void> lockAccount(@PathVariable Long userId) {
        try {
            userDetailsService.lockAccount(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}