package com.gaotianchi.client.web.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ImageResponse {
    private Long id;
    private String alt;
    private Map<String, String> urls;
}
