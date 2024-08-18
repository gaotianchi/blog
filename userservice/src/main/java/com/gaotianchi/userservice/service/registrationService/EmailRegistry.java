package com.gaotianchi.userservice.service.registrationService;

import com.gaotianchi.userservice.persistence.entity.User;
import com.gaotianchi.userservice.web.dto.RegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EmailRegistry implements Registry{
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmailRegistry(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public User register(RegistrationDto registrationDto) {
        final User user = new User();
        user.setPenName(registrationDto.getPenName());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setRegistrationMethod(registrationDto.getRegistrationMethod());
        user.setTimeZone(registrationDto.getTimeZone());
//        user.setRoles(Collections.singletonList());
        return null;
    }

}
