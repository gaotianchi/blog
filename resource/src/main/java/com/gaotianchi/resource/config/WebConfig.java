package com.gaotianchi.resource.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final ImageConfig imageConfig;

    public WebConfig(ImageConfig imageConfig) {
        this.imageConfig = imageConfig;
        checkRootPath();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(imageConfig.getUrlPattern())
                .addResourceLocations("file:" + imageConfig.getStorage().getRootPath() + "/")
                .setCachePeriod(3600)
                .resourceChain(true);
    }

    public void checkRootPath() {
        File file = new File(imageConfig.getStorage().getRootPath());
        if (!(file.exists() && file.isDirectory())) {
            file.mkdirs();
        }
    }
}
