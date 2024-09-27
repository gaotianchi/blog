package com.gaotianchi.resource.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties("pagination")
public class PaginationConfig {
    private NumberOfInfoPerPage numberOfInfoPerPage = new NumberOfInfoPerPage();

    @Setter
    @Getter
    public static class NumberOfInfoPerPage {
        private int userSeries;
        private int userIllustration;
        private int tag;
        private int userArticle;
        private int tagArticle;
        private int illustrationArticle;
        private int seriesArticle;
    }
}