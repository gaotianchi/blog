package com.gaotianchi.resourceservice.web.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResetPasswordRequest {
    private String newPassword;
}