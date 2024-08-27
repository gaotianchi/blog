package com.gaotianchi.authorizationservice.web.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class EmailUpdatedMessage {
    private String originalEmail;
    private String currentEmail;
    private OffsetDateTime updateData;
}
