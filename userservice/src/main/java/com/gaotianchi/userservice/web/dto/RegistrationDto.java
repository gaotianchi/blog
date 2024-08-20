package com.gaotianchi.userservice.web.dto;

import com.gaotianchi.userservice.enums.RegistrationMethod;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegistrationDto {
    private String penName;
    private String email;
    private String password;
    private RegistrationMethod registrationMethod;
}
