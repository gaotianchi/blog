package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.entity.SeriesEntity;
import com.gaotianchi.resourceservice.service.SeriesService;
import com.gaotianchi.resourceservice.web.dto.SeriesDto;
import com.gaotianchi.resourceservice.web.error.ArticleImageNotFoundException;
import com.gaotianchi.resourceservice.web.otd.SeriesOtd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeriesController {

    private final SeriesService seriesService;

    @Autowired
    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @PostMapping("/series/new")
    public ResponseEntity<SeriesOtd> newSeries(@RequestBody SeriesDto seriesDto) {
        try {
            SeriesEntity seriesEntity = seriesService.newSeries(seriesDto.getName(), seriesDto.getCoverId());
            return new ResponseEntity<>(new SeriesOtd(seriesEntity), HttpStatus.OK);
        } catch (ArticleImageNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
