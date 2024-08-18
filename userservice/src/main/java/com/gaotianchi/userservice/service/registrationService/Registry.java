package com.gaotianchi.userservice.service.registrationService;

import com.gaotianchi.userservice.persistence.entity.User;
import com.gaotianchi.userservice.web.dto.RegistrationDto;

public interface Registry {
    User register(RegistrationDto registrationDto);
}
