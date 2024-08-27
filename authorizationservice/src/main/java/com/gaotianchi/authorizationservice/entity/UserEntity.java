package com.gaotianchi.authorizationservice.entity;

import com.gaotianchi.authorizationservice.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;


@Entity
@Data
public class UserEntity implements UserDetails {


    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String password;
    private String roles;
    private OffsetDateTime lockedUntil;
    private OffsetDateTime registrationDateTime;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    private String privileges;

    public UserEntity() {
        this.registrationDateTime = OffsetDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.roles));
    }

    public void setRoles(List<String> roleList) {
        this.roles = StringUtils.collectionToCommaDelimitedString(roleList);
    }

    public Set<String> getRoles() {
        return StringUtils.commaDelimitedListToSet(this.roles);
    }

    public void setPrivileges(List<String> roleList) {
        this.roles = StringUtils.collectionToCommaDelimitedString(roleList);
    }

    public Set<String> getPrivileges() {
        return StringUtils.commaDelimitedListToSet(this.privileges);
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

}
