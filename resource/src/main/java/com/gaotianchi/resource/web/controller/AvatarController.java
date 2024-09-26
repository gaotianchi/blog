package com.gaotianchi.resource.web.controller;

import com.gaotianchi.resource.web.response.APIResponse;
import com.gaotianchi.resource.web.response.info.AvatarInfo;
import com.gaotianchi.resource.web.service.avatar.AvatarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping("/avatar/info/{id}")
    public APIResponse<AvatarInfo> getInfo(@PathVariable Long id) {
        AvatarInfo avatarInfo = avatarService.getInfo(id);
        return APIResponse.success(avatarInfo);
    }
}
