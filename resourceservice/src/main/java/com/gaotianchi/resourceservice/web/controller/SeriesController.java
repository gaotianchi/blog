package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.service.SeriesService;
import com.gaotianchi.resourceservice.web.error.EntityNotFoundException;
import com.gaotianchi.resourceservice.web.request.NewSeriesRequest;
import com.gaotianchi.resourceservice.web.response.ArticleResponse;
import com.gaotianchi.resourceservice.web.response.SeriesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<SeriesResponse> newSeries(@AuthenticationPrincipal UserDetails userDetails, @RequestBody NewSeriesRequest newSeriesRequest) {
        try {
            SeriesResponse seriesResponse = seriesService.newSeries(userDetails.getUsername(), newSeriesRequest.getName(), newSeriesRequest.getCoverId());
            return new ResponseEntity<>(seriesResponse, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/series/list")
    public ResponseEntity<List<SeriesResponse>> newSeries(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            List<SeriesResponse> seriesResponses = seriesService.listSeries(userDetails.getUsername());
            return new ResponseEntity<>(seriesResponses, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/series/list-articles/{seriesId}")
    public ResponseEntity<List<ArticleResponse>> listArticles(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long seriesId) {
        try {
            List<ArticleResponse> ArticleResponses = seriesService.listArticles(userDetails.getUsername(), seriesId);
            return new ResponseEntity<>(ArticleResponses, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/series/delete/{id}")
    public ResponseEntity<Void> deleteSeries(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        try {
            seriesService.deleteSeries(userDetails.getUsername(), id);
            return null;
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
