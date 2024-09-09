package com.gaotianchi.resourceservice.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class TestController {
    @GetMapping("/resource/article")
    public List<String> article() {
        return Arrays.asList("article1", "article2", "article3");
    }
}
