package com.gaotianchi.resource.web.controller;

import com.gaotianchi.resource.web.request.NewSeriesRequest;
import com.gaotianchi.resource.web.request.UpdateSeriesInfoRequest;
import com.gaotianchi.resource.web.response.APIResponse;
import com.gaotianchi.resource.web.response.PageInfo;
import com.gaotianchi.resource.web.response.info.SeriesCoverInfo;
import com.gaotianchi.resource.web.response.info.SeriesInfo;
import com.gaotianchi.resource.web.service.series.SeriesService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class SeriesController {

    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @PostMapping("/series/new")
    public APIResponse<SeriesInfo> newSeries(@AuthenticationPrincipal Jwt jwt, @RequestBody NewSeriesRequest newSeriesRequest) {
        return APIResponse.success(seriesService.newSeries(jwt.getSubject(), newSeriesRequest.getTitle(), newSeriesRequest.getProfile()));
    }

    @DeleteMapping("/series/delete/{id}")
    public APIResponse<Void> deleteSeries(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) throws IOException {
        seriesService.deleteSeries(jwt.getSubject(), id);
        return APIResponse.success();
    }

    @PatchMapping("/series/info/{id}")
    public APIResponse<Void> updateInfo(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id, @RequestBody UpdateSeriesInfoRequest updateSeriesInfoRequest) {
        seriesService.updateInfo(jwt.getSubject(), id, updateSeriesInfoRequest.getTitle(), updateSeriesInfoRequest.getProfile());
        return APIResponse.success();
    }

    @PostMapping("/series/cover/{id}/{coverId}")
    public APIResponse<SeriesCoverInfo> setCover(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id, @PathVariable Long coverId) throws IOException {
        return APIResponse.success(seriesService.setCover(jwt.getSubject(), id, coverId));
    }

    @DeleteMapping("/series/cover/{id}")
    public APIResponse<Void> removeCover(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) throws IOException {
        seriesService.removeCover(jwt.getSubject(), id);
        return APIResponse.success();
    }

    @GetMapping("/series/info/{id}")
    public APIResponse<SeriesInfo> getInfo(@PathVariable Long id) {
        return APIResponse.success(seriesService.getInfo(id));
    }

    @GetMapping("/series/user/{userId}")
    public APIResponse<PageInfo<SeriesInfo>> getUserSeriesInfoPage(@PathVariable Long userId, @RequestParam(value = "page", defaultValue = "0", required = false) int page) {
        return APIResponse.success(seriesService.getUserSeriesInfoPage(userId, page));
    }
}
