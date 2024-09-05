package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.service.UserService;
import com.gaotianchi.resourceservice.web.request.NewUserRequest;
import com.gaotianchi.resourceservice.web.request.ResetPasswordRequest;
import com.gaotianchi.resourceservice.web.request.UpdateUserInfoRequest;
import com.gaotianchi.resourceservice.web.response.APIResponse;
import com.gaotianchi.resourceservice.web.response.ArticleResponse;
import com.gaotianchi.resourceservice.web.response.SeriesResponse;
import com.gaotianchi.resourceservice.web.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/new")
    public APIResponse<UserResponse> newUser(@RequestBody NewUserRequest newUserRequest) {
        UserResponse userResponse = userService.newUser(newUserRequest.getPenName(), newUserRequest.getEmail(), newUserRequest.getPassword());
        return APIResponse.success(userResponse);
    }

    @GetMapping("/blogger/list-users")
    public APIResponse<List<UserResponse>> listUsers() {
        List<UserResponse> userResponses = userService.listUsers();
        return APIResponse.success(userResponses);
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

    @PatchMapping("/users/set-avatar/{imageId}")
    public APIResponse<UserResponse> setAvatar(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long imageId) {
        UserResponse userResponse = userService.setAvatar(userDetails.getUsername(), imageId);
        return APIResponse.success(userResponse);
    }

    @PatchMapping("/users/update-info")
    public APIResponse<UserResponse> updateInfo(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UpdateUserInfoRequest updateUserInfoRequest) {
        UserResponse userResponse = userService.updateUserInfo(userDetails.getUsername(), updateUserInfoRequest.getPenName());
        return APIResponse.success(userResponse);
    }

    @PatchMapping("/users/reset-password")
    public APIResponse<UserResponse> resetPassword(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ResetPasswordRequest resetPasswordRequest) {
        UserResponse userResponse = userService.resetPassword(userDetails.getUsername(), resetPasswordRequest.getNewPassword());
        return APIResponse.success(userResponse);
    }

    @GetMapping("/users/list-articles")
    public APIResponse<List<ArticleResponse>> listArticles(@AuthenticationPrincipal UserDetails userDetails) {
        List<ArticleResponse> articleResponses = userService.listArticles(userDetails.getUsername());
        return APIResponse.success(articleResponses);
    }

    @GetMapping("/users/list-series")
    public APIResponse<List<SeriesResponse>> listSeries(@AuthenticationPrincipal UserDetails userDetails) {
        List<SeriesResponse> seriesResponses = userService.listSeries(userDetails.getUsername());
        return APIResponse.success(seriesResponses);
    }
}
