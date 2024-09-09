package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.web.request.NewUserRequest;
import com.gaotianchi.resourceservice.web.request.UpdateUserInfoRequest;
import com.gaotianchi.resourceservice.web.response.APIResponse;
import com.gaotianchi.resourceservice.web.response.ArticleResponse;
import com.gaotianchi.resourceservice.web.response.SeriesResponse;
import com.gaotianchi.resourceservice.web.response.UserResponse;
import com.gaotianchi.resourceservice.web.service.userservice.UserService;
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
        UserResponse userResponse = userService.newUser(newUserRequest.getPenName(), newUserRequest.getEmail());
        return APIResponse.success(userResponse);
    }

    @GetMapping("/blogger/list-users")
    public APIResponse<List<UserResponse>> listUsers() {
        List<UserResponse> userResponses = userService.listUsers();
        return APIResponse.success(userResponses);
    }

    @PatchMapping("/users/set-avatar/{imageId}")
    public APIResponse<UserResponse> setAvatar(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long imageId) {
        UserResponse userResponse = userService.updateAvatar(userDetails.getUsername(), imageId);
        return APIResponse.success(userResponse);
    }

    @PatchMapping("/users/update-info")
    public APIResponse<UserResponse> updateInfo(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UpdateUserInfoRequest updateUserInfoRequest) {
        UserResponse userResponse = userService.updateInfo(userDetails.getUsername(), updateUserInfoRequest.getPenName());
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
