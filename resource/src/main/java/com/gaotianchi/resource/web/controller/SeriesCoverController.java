package com.gaotianchi.resource.web.controller;

import com.gaotianchi.resource.web.response.APIResponse;
import com.gaotianchi.resource.web.response.info.SeriesCoverInfo;
import com.gaotianchi.resource.web.service.series.cover.SeriesCoverService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class SeriesCoverController {
    private final SeriesCoverService seriesCoverService;

    public SeriesCoverController(SeriesCoverService seriesCoverService) {
        this.seriesCoverService = seriesCoverService;
    }

    @PostMapping("/series-covers/new")
    public APIResponse<SeriesCoverInfo> newSeriesCover(@AuthenticationPrincipal Jwt jwt, MultipartFile file) throws IOException {
        return APIResponse.success(seriesCoverService.newSeriesCover(jwt.getSubject(), file));
    }

    @DeleteMapping("/series-cover/delete/{id}")
    public APIResponse<Void> deleteSeriesCover(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) throws IOException {
        seriesCoverService.deleteSeriesCover(jwt.getSubject(), id);
        return APIResponse.success();
    }

    @GetMapping("/series-cover/info/{id}")
    public APIResponse<SeriesCoverInfo> getInfo(@PathVariable Long id) {
        return APIResponse.success(seriesCoverService.getInfo(id));
    }

}
