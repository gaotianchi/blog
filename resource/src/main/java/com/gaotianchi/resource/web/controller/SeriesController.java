package com.gaotianchi.resource.web.controller;

import com.gaotianchi.resource.web.request.NewSeriesRequest;
import com.gaotianchi.resource.web.response.APIResponse;
import com.gaotianchi.resource.web.response.ArticleResponse;
import com.gaotianchi.resource.web.response.SeriesResponse;
import com.gaotianchi.resource.web.service.seriesservice.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SeriesController {

    private final SeriesService seriesService;

    @Autowired
    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @PostMapping("/series/new")
    public APIResponse<SeriesResponse> newSeries(@AuthenticationPrincipal Jwt jwt, @RequestBody NewSeriesRequest newSeriesRequest) {
        SeriesResponse seriesResponse = seriesService.newSeries(jwt.getSubject(), newSeriesRequest.getName(), newSeriesRequest.getCoverId());
        return APIResponse.success(seriesResponse);
    }

    @GetMapping("/series/list")
    public APIResponse<List<SeriesResponse>> listSeries(@AuthenticationPrincipal Jwt jwt) {
        List<SeriesResponse> seriesResponses = seriesService.listSeries(jwt.getSubject());
        return APIResponse.success(seriesResponses);
    }

    @GetMapping("/series/list-articles/{seriesId}")
    public APIResponse<List<ArticleResponse>> listArticles(@AuthenticationPrincipal Jwt jwt, @PathVariable Long seriesId) {
        List<ArticleResponse> articleResponses = seriesService.listArticles(jwt.getSubject(), seriesId);
        return APIResponse.success(articleResponses);
    }

    @DeleteMapping("/series/delete/{id}")
    public APIResponse<Void> deleteSeries(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        seriesService.deleteSeries(jwt.getSubject(), id);
        return APIResponse.success();
    }
}
