package com.gaotianchi.userservice.persistence.entity;

import com.gaotianchi.userservice.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;
import java.util.TimeZone;

@Entity
@Data
@AllArgsConstructor
public class User {
    @Id
    private String email;
    private String penName;
    private TimeZone timeZone;
    private String avatar_url;
    private String personalWebsite;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;

    public User() {

    }
}
