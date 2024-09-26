package com.gaotianchi.resource.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Setter
@Getter
@Configuration
@ConfigurationProperties("storage")
public class StorageConfig {
    private Illustration illustration = new Illustration();
    private SeriesCover seriesCover = new SeriesCover();
    private Avatar avatar = new Avatar();

    @Setter
    @Getter
    public static class Illustration {
        private String location;
        private List<String> allowedExtensions;
        private String originalPrefix;
        private String thumbnailPrefix;
        private int maxWidth;
        private double quality;
    }

    @Setter
    @Getter
    public static class SeriesCover {
        private String location;
        private List<String> allowedExtensions;
        private String originalPrefix;
        private String thumbnailPrefix;
        private int maxWidth;
        private double quality;
    }

    @Setter
    @Getter
    public static class Avatar {
        private String location;
        private List<String> allowedExtensions;
    }
}
