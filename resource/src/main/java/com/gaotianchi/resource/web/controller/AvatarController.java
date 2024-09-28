package com.gaotianchi.resource.web.controller;

import com.gaotianchi.resource.web.response.APIResponse;
import com.gaotianchi.resource.web.response.info.AvatarInfo;
import com.gaotianchi.resource.web.service.user.avatar.AvatarService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping("/avatar/new")
    public APIResponse<AvatarInfo> newAvatar(@AuthenticationPrincipal Jwt jwt, MultipartFile file) throws IOException {
        return APIResponse.success(avatarService.newAvatar(jwt.getSubject(), file));
    }

    @DeleteMapping("/avatar/delete")
    public APIResponse<Void> newAvatar(@AuthenticationPrincipal Jwt jwt) throws IOException {
        avatarService.deleteAvatar(jwt.getSubject());
        return APIResponse.success();
    }

    @GetMapping("/avatar/info/{id}")
    public APIResponse<AvatarInfo> getInfo(@PathVariable Long id) {
        AvatarInfo avatarInfo = avatarService.getInfo(id);
        return APIResponse.success(avatarInfo);
    }
}
