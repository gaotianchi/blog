package com.gaotianchi.client.web.controller;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@Controller
public class TestController {
    private final WebClient webClient;

    public TestController(WebClient webClient) {
        this.webClient = webClient;
    }

    @RequestMapping("/test")
    public String test(Model model, @RegisteredOAuth2AuthorizedClient("blog-client") OAuth2AuthorizedClient authorizedClient) {
        String subject = webClient
                .get()
                .uri("http://localhost:8090/subject")
                .attributes(oauth2AuthorizedClient(authorizedClient))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        model.addAttribute("subject", subject);
        return "test";
    }
}
