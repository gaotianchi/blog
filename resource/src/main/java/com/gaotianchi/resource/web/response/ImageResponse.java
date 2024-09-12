package com.gaotianchi.resource.web.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaotianchi.resource.persistence.entity.ImageEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ImageResponse {
    private Long id;
    private String alt;
    private Map<String, String> urls;

    public ImageResponse(ImageEntity imageEntity) {
        setupData(imageEntity);
    }

    private void setupData(ImageEntity imageEntity) {
        this.id = imageEntity.getId();
        this.alt = imageEntity.getAlt();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.urls = objectMapper.readValue(imageEntity.getUrls(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            this.urls = new HashMap<>();
        }
    }
}
