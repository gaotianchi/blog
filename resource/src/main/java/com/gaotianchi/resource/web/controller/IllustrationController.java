package com.gaotianchi.resource.web.controller;

import com.gaotianchi.resource.web.request.UpdateIllustrationInfoRequest;
import com.gaotianchi.resource.web.response.APIResponse;
import com.gaotianchi.resource.web.response.PageInfo;
import com.gaotianchi.resource.web.response.info.IllustrationInfo;
import com.gaotianchi.resource.web.service.Illustration.IllustrationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class IllustrationController {

    private final IllustrationService illustrationService;

    public IllustrationController(IllustrationService illustrationService) {
        this.illustrationService = illustrationService;
    }

    @PostMapping("/illustrations/new")
    public APIResponse<IllustrationInfo> newIllustration(@AuthenticationPrincipal Jwt jwt, MultipartFile file, String title, String alt) throws IOException {
        return APIResponse.success(illustrationService.newIllustration(jwt.getSubject(), file, title, alt));
    }

    @DeleteMapping("/illustrations/delete/{id}")
    public APIResponse<Void> deleteIllustration(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) throws IOException {
        illustrationService.deleteIllustration(jwt.getSubject(), id);
        return APIResponse.success();
    }

    @PatchMapping("/illustrations/info/{id}")
    public APIResponse<Void> updateContent(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id, @RequestBody UpdateIllustrationInfoRequest updateIllustrationInfoRequest) throws IOException {
        illustrationService.updateInfo(jwt.getSubject(), id, updateIllustrationInfoRequest.getTitle(), updateIllustrationInfoRequest.getAlt());
        return APIResponse.success();
    }

    @GetMapping("/illustrations/info/{id}")
    public APIResponse<IllustrationInfo> getInfo(@PathVariable Long id) {
        IllustrationInfo illustrationInfo = illustrationService.getInfo(id);
        return APIResponse.success(illustrationInfo);
    }

    @GetMapping("/illustrations/user/{userId}")
    public APIResponse<PageInfo<IllustrationInfo>> getUserIllustrationInfoPage(@PathVariable Long userId, @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        return APIResponse.success(illustrationService.getUserIllustrationInfoPage(userId, page));
    }

    @GetMapping("/illustrations/article/{articleId}")
    public APIResponse<PageInfo<IllustrationInfo>> getArticleIllustrationInfoPage(@PathVariable Long articleId, @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        return APIResponse.success(illustrationService.getArticleIllustrationInfoPage(articleId, page));
    }
}
