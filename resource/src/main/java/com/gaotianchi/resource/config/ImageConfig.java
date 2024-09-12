package com.gaotianchi.resource.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Setter
@Getter
@Configuration
@ConfigurationProperties("image")
public class ImageConfig {
    private List<String> supportedExtensions;
    private Storage storage = new Storage();
    private Compression compression = new Compression();
    private String urlPattern;

    @Setter
    @Getter
    public static class Storage {
        private String RootPath;
    }

    @Setter
    @Getter
    public static class Compression {
        private boolean enable = true;
    }
}
