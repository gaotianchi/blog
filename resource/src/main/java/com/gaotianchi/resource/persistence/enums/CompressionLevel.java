package com.gaotianchi.resource.persistence.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum CompressionLevel {
    ORIGINAL(1.0F),
    MEDIUM(0.5F),
    LOW(0.1F);

    private final float rate;
}
