package com.gaotianchi.auth.web.controller;

import com.gaotianchi.auth.web.request.NewUserRequest;
import com.gaotianchi.auth.web.request.UpdatePasswordRequest;
import com.gaotianchi.auth.web.request.UpdateUsernameRequest;
import com.gaotianchi.auth.web.response.APIResponse;
import com.gaotianchi.auth.web.response.UserResponse;
import com.gaotianchi.auth.web.service.userservice.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/new")
    public APIResponse<UserResponse> newUser(@RequestBody NewUserRequest newUserRequest) {
        UserResponse userResponse = userService.newUser(newUserRequest.getUsername(), newUserRequest.getPassword());
        return APIResponse.success(userResponse);
    }

    @GetMapping("/users/get-info")
    public APIResponse<UserResponse> getInfo(@AuthenticationPrincipal Jwt jwt) {
        UserResponse userResponse = userService.getInfo(jwt.getSubject());
        return APIResponse.success(userResponse);
    }

    @PatchMapping("/users/update-username")
    public APIResponse<UserResponse> updateUsername(@AuthenticationPrincipal Jwt jwt, @RequestBody UpdateUsernameRequest updateUsernameRequest) {
        UserResponse userResponse = userService.updateUsername(jwt.getSubject(), updateUsernameRequest.getUsername());
        return APIResponse.success(userResponse);
    }

    @PatchMapping("/users/update-password")
    public APIResponse<UserResponse> updatePassword(@AuthenticationPrincipal Jwt jwt, @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        UserResponse userResponse = userService.updatePassword(jwt.getSubject(), updatePasswordRequest.getPassword());
        return APIResponse.success(userResponse);
    }

    @PatchMapping("/users/deregister")
    public APIResponse<UserResponse> deregister(@AuthenticationPrincipal Jwt jwt) {
        UserResponse userResponse = userService.deregister(jwt.getSubject());
        return APIResponse.success(userResponse);
    }

    @PatchMapping("/blogger/lock/{userId}")
    public APIResponse<UserResponse> lockUser(@PathVariable Long userId) {
        UserResponse userResponse = userService.lockUser(userId);
        return APIResponse.success(userResponse);
    }

    @PatchMapping("/blogger/unlock/{userId}")
    public APIResponse<UserResponse> unlockUser(@PathVariable Long userId) {
        UserResponse userResponse = userService.unlockUser(userId);
        return APIResponse.success(userResponse);
    }
}
