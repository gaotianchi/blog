package com.gaotianchi.resourceservice.web.controller;

import com.gaotianchi.resourceservice.entity.SeriesEntity;
import com.gaotianchi.resourceservice.service.SeriesService;
import com.gaotianchi.resourceservice.web.dto.SeriesDto;
import com.gaotianchi.resourceservice.web.dto.UpdateSeriesCoverDto;
import com.gaotianchi.resourceservice.web.dto.UpdateSeriesInfoDto;
import com.gaotianchi.resourceservice.web.error.ArticleImageNotFoundException;
import com.gaotianchi.resourceservice.web.error.SeriesNotFoundException;
import com.gaotianchi.resourceservice.web.otd.SeriesOtd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @DeleteMapping("/series/delete/{id}")
    public ResponseEntity<Void> deleteSeries(@PathVariable Long id) {
        try {
            seriesService.deleteSeries(id);
            return null;
        } catch (SeriesNotFoundException e) {
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
        } catch (ArticleImageNotFoundException | SeriesNotFoundException e) {
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
}
