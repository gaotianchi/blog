package com.gaotianchi.resource.web.controller;

import com.gaotianchi.resource.web.request.UpdateUserInfoRequest;
import com.gaotianchi.resource.web.response.APIResponse;
import com.gaotianchi.resource.web.response.info.UserInfo;
import com.gaotianchi.resource.web.service.user.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping("/users/info")
    public APIResponse<Void> updateInfo(@AuthenticationPrincipal Jwt jwt, @RequestBody UpdateUserInfoRequest updateUserInfoRequest) {
        userService.updateInfo(jwt.getSubject(), updateUserInfoRequest.getPenName(), updateUserInfoRequest.getProfile(), updateUserInfoRequest.getTimeZone());
        return APIResponse.success();
    }

    @GetMapping("/users/info/{id}")
    public APIResponse<UserInfo> getPublicInfo(@PathVariable Long id) {
        UserInfo userInfo = userService.getPublicInfo(id);
        return APIResponse.success(userInfo);
    }

    @GetMapping("/users/info")
    public APIResponse<UserInfo> getPrivateInfo(@AuthenticationPrincipal Jwt jwt) {
        UserInfo userInfo = userService.getPrivateInfo(jwt.getSubject());
        return APIResponse.success(userInfo);
    }
}
