package com.gaotianchi.userservice.persistence.entity;

import com.gaotianchi.userservice.enums.AccountStatus;
import com.gaotianchi.userservice.enums.RegistrationMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;
import java.util.Base64;

@Entity
@Data
@AllArgsConstructor
@Builder
public class User {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    @Column(length = 60)
    private String password;
    private String penName;
    private RegistrationMethod registrationMethod;
    private String avatar_url;
    private OffsetDateTime registrationTime;
    private TimeZone timeZone;
    private String personalWebsite;
    private AccountStatus accountStatus;
    private String secret;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;

    public User() {
        super();
        this.secret = generateBase64RandomCode();
        this.accountStatus = AccountStatus.NOT_ACTIVATED;
        this.registrationTime = OffsetDateTime.now();
    }

    public String generateBase64RandomCode() {
        String randomUUID = UUID.randomUUID().toString();
        byte[] bytes = randomUUID.getBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }
}
