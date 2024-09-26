package com.gaotianchi.resource.web.controller;

import com.gaotianchi.resource.web.response.APIResponse;
import com.gaotianchi.resource.web.response.info.UserInfo;
import com.gaotianchi.resource.web.service.user.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/info/{id}")
    public APIResponse<UserInfo> getInfo(@PathVariable Long id) {
        UserInfo userInfo = userService.getInfo(id);
        return APIResponse.success(userInfo);
    }
}
