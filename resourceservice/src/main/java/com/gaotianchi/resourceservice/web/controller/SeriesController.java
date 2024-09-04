package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.error.EntityNotFoundException;
import com.gaotianchi.resourceservice.error.ImageNotFoundException;
import com.gaotianchi.resourceservice.error.SeriesNotFoundException;
import com.gaotianchi.resourceservice.persistence.entity.SeriesEntity;
import com.gaotianchi.resourceservice.service.SeriesService;
import com.gaotianchi.resourceservice.web.request.NewSeriesRequest;
import com.gaotianchi.resourceservice.web.request.UpdateSeriesCoverDto;
import com.gaotianchi.resourceservice.web.request.UpdateSeriesInfoDto;
import com.gaotianchi.resourceservice.web.response.ArticleResponse;
import com.gaotianchi.resourceservice.web.response.SeriesOtd;
import com.gaotianchi.resourceservice.web.response.SeriesResponse;
import com.gaotianchi.resourceservice.web.response.SeriesWithArticlesOtd;
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

    @PatchMapping("/series/info/{id}")
    public ResponseEntity<SeriesOtd> updateSeriesInfo(@PathVariable Long id, @RequestBody UpdateSeriesInfoDto updateSeriesInfoDto) {
        try {
            SeriesEntity seriesEntity = seriesService.updateSeriesInfo(id, updateSeriesInfoDto.getName());
            return new ResponseEntity<>(new SeriesOtd(seriesEntity), HttpStatus.OK);
        } catch (SeriesNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/series/cover/{id}")
    public ResponseEntity<SeriesOtd> updateSeriesCover(@PathVariable Long id, @RequestBody UpdateSeriesCoverDto updateSeriesCoverDto) {
        try {
            SeriesEntity seriesEntity = seriesService.updateSeriesCover(id, updateSeriesCoverDto.getCoverId());
            return new ResponseEntity<>(new SeriesOtd(seriesEntity), HttpStatus.OK);
        } catch (ImageNotFoundException | SeriesNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/series/info/{id}")
    public ResponseEntity<SeriesOtd> getSeriesInfo(@PathVariable Long id) {
        try {
            SeriesEntity seriesEntity = seriesService.getSeriesInfo(id);
            return new ResponseEntity<>(new SeriesOtd(seriesEntity), HttpStatus.OK);
        } catch (SeriesNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/series/details/{id}")
    public ResponseEntity<SeriesWithArticlesOtd> getSeriesDetails(@PathVariable Long id) {
        try {
            SeriesEntity seriesEntity = seriesService.getSeriesEntityOrNotFound(id);
            return new ResponseEntity<>(new SeriesWithArticlesOtd(seriesEntity), HttpStatus.OK);
        } catch (SeriesNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/series/all")
    public ResponseEntity<List<SeriesOtd>> getAllSeries() {
        try {
            List<SeriesOtd> seriesOtds = seriesService.getAllSeries();
            return new ResponseEntity<>(seriesOtds, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
