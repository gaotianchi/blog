package com.gaotianchi.resource.event;

import com.gaotianchi.resource.persistence.enums.CompressionLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.nio.file.Path;
import java.util.Map;

@Setter
@Getter
public class ImageSavedEvent extends ApplicationEvent {
    private Map<CompressionLevel, Path> compressionPathMap;

    public ImageSavedEvent(Object source, Map<CompressionLevel, Path> compressionPathMap) {
        super(source);
        this.compressionPathMap = compressionPathMap;
    }
}
