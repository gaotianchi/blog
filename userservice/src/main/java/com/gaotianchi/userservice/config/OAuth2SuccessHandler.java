package com.gaotianchi.userservice.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final WebClient webClient;
    @Autowired
    public OAuth2SuccessHandler(OAuth2AuthorizedClientService authorizedClientService, WebClient webClient) {
        this.authorizedClientService = authorizedClientService;
        this.webClient = webClient;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        if (authentication instanceof OAuth2AuthenticationToken oauth2Token) {
            String clientRegistrationId = oauth2Token.getAuthorizedClientRegistrationId();
            String principalName = oauth2Token.getName();
            OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(clientRegistrationId, principalName);
            String accessToken = authorizedClient.getAccessToken().getTokenValue();
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/users")
                    .queryParam("method", clientRegistrationId);
            webClient.post().uri(uriComponentsBuilder.build().toUri()).header("Authorization", "Bearer " + accessToken).retrieve().toBodilessEntity().block();
            response.sendRedirect("/");
        }
    }
}
