package com.gaotianchi.resourceservice.persistence.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseErrorCode {
    SUCCESS(0, "success."),
    FAIL(1, "failed."),

    AUTHORIZATION_FAILED(403, "Authorization failed."),
    AUTHENTICATION_FAILED(401, "Authentication failed.");

    private final Integer code;
    private final String message;
}
