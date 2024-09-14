package com.gaotianchi.resource.web.controller;

import com.gaotianchi.resource.web.request.NewUserRequest;
import com.gaotianchi.resource.web.request.UpdateUserInfoRequest;
import com.gaotianchi.resource.web.response.APIResponse;
import com.gaotianchi.resource.web.response.ArticleResponse;
import com.gaotianchi.resource.web.response.SeriesResponse;
import com.gaotianchi.resource.web.response.UserResponse;
import com.gaotianchi.resource.web.service.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
    public APIResponse<UserResponse> setAvatar(@AuthenticationPrincipal Jwt jwt, @PathVariable Long imageId) {
        UserResponse userResponse = userService.updateAvatar(jwt.getSubject(), imageId);
        return APIResponse.success(userResponse);
    }

    @PatchMapping("/users/update-info")
    public APIResponse<UserResponse> updateInfo(@AuthenticationPrincipal Jwt jwt, @RequestBody UpdateUserInfoRequest updateUserInfoRequest) {
        UserResponse userResponse = userService.updateInfo(jwt.getSubject(), updateUserInfoRequest.getPenName());
        return APIResponse.success(userResponse);
    }

    @GetMapping("/users/list-articles")
    public APIResponse<List<ArticleResponse>> listArticles(@AuthenticationPrincipal Jwt jwt) {
        List<ArticleResponse> articleResponses = userService.listArticles(jwt.getSubject());
        return APIResponse.success(articleResponses);
    }

    @GetMapping("/users/list-series")
    public APIResponse<List<SeriesResponse>> listSeries(@AuthenticationPrincipal Jwt jwt) {
        List<SeriesResponse> seriesResponses = userService.listSeries(jwt.getSubject());
        return APIResponse.success(seriesResponses);
    }

    @GetMapping("/users/get-info")
    public APIResponse<UserResponse> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        UserResponse userResponse = userService.getUserInfo(jwt.getSubject());
        return APIResponse.success(userResponse);
    }
}
