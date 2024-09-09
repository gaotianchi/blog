package com.gaotianchi.resource.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
@Setter
@Getter
public class StorageProperties {
    private String rootLocation;
    private String articleImageUri;
    private String rootUrl;
}
