package com.gaotianchi.oauth2service.persistence.entity;

import com.gaotianchi.oauth2service.persistence.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private OffsetDateTime lockedUntil;
    private OffsetDateTime registrationDateTime;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<RoleEntity> roles = new ArrayList<>();

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (this.accountStatus == AccountStatus.LOCKED && this.getLockedUntil() != null) {
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
        return !(this.accountStatus == AccountStatus.DEREGISTERED);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (RoleEntity roleEntity : this.roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleEntity.getRoleType().toString()));
            for (PrivilegeEntity privilegeEntity : roleEntity.getPrivileges()) {
                authorities.add(new SimpleGrantedAuthority(privilegeEntity.getPrivilegeType().toString()));
            }
        }
        return authorities;
    }
}
