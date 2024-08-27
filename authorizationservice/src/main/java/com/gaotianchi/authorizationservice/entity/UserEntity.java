package com.gaotianchi.authorizationservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;


@Entity
@Data
public class UserEntity implements UserDetails {


    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String password;
    private String role;
    private OffsetDateTime lockedUntil;
    private OffsetDateTime registrationDateTime;

    public UserEntity() {
        this.registrationDateTime = OffsetDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (Objects.equals(this.role, "LOCKED_SUBSCRIBER") && this.getLockedUntil() != null) {
            return OffsetDateTime.now().isAfter(this.getLockedUntil());
        }
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !Objects.equals(this.role, "DEREGISTERED_SUBSCRIBER");
    }

}
