package com.gaotianchi.oauth2service.web.controller;

import com.gaotianchi.oauth2service.service.userservice.UserService;
import com.gaotianchi.oauth2service.web.request.NewUserRequest;
import com.gaotianchi.oauth2service.web.request.UpdatePasswordRequest;
import com.gaotianchi.oauth2service.web.request.UpdateUsernameRequest;
import com.gaotianchi.oauth2service.web.response.APIResponse;
import com.gaotianchi.oauth2service.web.response.UserResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @GetMapping("/users/info")
    public APIResponse<UserResponse> getInfo(@AuthenticationPrincipal UserDetails userDetails) {
        UserResponse userResponse = userService.getInfo(userDetails.getUsername());
        return APIResponse.success(userResponse);
    }

    @PatchMapping("/users/update-username")
    public APIResponse<UserResponse> updateUsername(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UpdateUsernameRequest updateUsernameRequest) {
        UserResponse userResponse = userService.updateUsername(userDetails.getUsername(), updateUsernameRequest.getUsername());
        return APIResponse.success(userResponse);
    }

    @PatchMapping("/users/update-password")
    public APIResponse<UserResponse> updatePassword(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        UserResponse userResponse = userService.updatePassword(userDetails.getUsername(), updatePasswordRequest.getPassword());
        return APIResponse.success(userResponse);
    }

    @PatchMapping("/users/deregister")
    public APIResponse<UserResponse> deregister(@AuthenticationPrincipal UserDetails userDetails) {
        UserResponse userResponse = userService.deregister(userDetails.getUsername());
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
