package com.gaotianchi.resource.web.controller;

import com.gaotianchi.resource.web.request.NewSeriesRequest;
import com.gaotianchi.resource.web.response.APIResponse;
import com.gaotianchi.resource.web.response.ArticleResponse;
import com.gaotianchi.resource.web.response.SeriesResponse;
import com.gaotianchi.resource.web.service.seriesservice.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public APIResponse<SeriesResponse> newSeries(@AuthenticationPrincipal UserDetails userDetails, @RequestBody NewSeriesRequest newSeriesRequest) {
        SeriesResponse seriesResponse = seriesService.newSeries(userDetails.getUsername(), newSeriesRequest.getName(), newSeriesRequest.getCoverId());
        return APIResponse.success(seriesResponse);
    }

    @GetMapping("/series/list")
    public APIResponse<List<SeriesResponse>> listSeries(@AuthenticationPrincipal UserDetails userDetails) {
        List<SeriesResponse> seriesResponses = seriesService.listSeries(userDetails.getUsername());
        return APIResponse.success(seriesResponses);
    }

    @GetMapping("/series/list-articles/{seriesId}")
    public APIResponse<List<ArticleResponse>> listArticles(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long seriesId) {
        List<ArticleResponse> articleResponses = seriesService.listArticles(userDetails.getUsername(), seriesId);
        return APIResponse.success(articleResponses);
    }

    @DeleteMapping("/series/delete/{id}")
    public APIResponse<Void> deleteSeries(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        seriesService.deleteSeries(userDetails.getUsername(), id);
        return APIResponse.success();
    }
}
