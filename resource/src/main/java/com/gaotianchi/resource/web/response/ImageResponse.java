package com.gaotianchi.resource.web.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaotianchi.resource.persistence.entity.ImageEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Map;

@Getter
@Setter
public class ImageResponse {
    private Long id;
    private String alt;
    private Map<String, String> urls;
    private boolean forAvatar;
    private boolean forArticle;
    private boolean forSeries;
    private OffsetDateTime creationDatetime;
    private OffsetDateTime updateDatetime;
    private String title;

    public ImageResponse(ImageEntity imageEntity) {
        setupData(imageEntity);
    }

    private void setupData(ImageEntity imageEntity) {
        this.id = imageEntity.getId();
        this.alt = imageEntity.getAlt();
        this.urls = parseUrls(imageEntity.getUrls());
        this.forArticle = imageEntity.isForArticle();
        this.forAvatar = imageEntity.isForAvatar();
        this.forSeries = imageEntity.isForSeries();
        this.creationDatetime = imageEntity.getCreationDatetime();
        this.updateDatetime = imageEntity.getUpdateDatetime();
        this.title = imageEntity.getTitle();
    }

    private Map<String, String> parseUrls(String urlsJson) {
        if (urlsJson == null || urlsJson.isEmpty()) {
            return Collections.emptyMap(); // 如果 urls 为空，返回空的 Map
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(urlsJson, new TypeReference<Map<String, String>>() {
            });
        } catch (JsonProcessingException e) {
            // 处理解析异常，并提供详细错误信息
            throw new RuntimeException("Failed to parse URLs JSON: " + urlsJson, e);
        }
    }
}
