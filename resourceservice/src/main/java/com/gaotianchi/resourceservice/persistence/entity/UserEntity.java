package com.gaotianchi.resourceservice.persistence.entity;

import com.gaotianchi.resourceservice.persistence.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.*;

@Entity
@Getter
@Setter
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String penName;
    private String password;
    private OffsetDateTime lockedUntil;
    private OffsetDateTime registrationDateTime;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    private TimeZone timeZone;
    private Integer score = 0;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<RoleEntity> roles = new ArrayList<>();

    @ManyToOne
    private ImageEntity avatar;

    @OneToMany(mappedBy = "author")
    private Collection<ArticleEntity> articleEntities = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private Collection<CommentEntity> commentEntities = new ArrayList<>();

    @OneToMany(mappedBy = "voter")
    private Collection<ArticleVoteRecordEntity> voteEntities = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity")
    private Collection<AccessRecordEntity> accessRecordEntities = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private Collection<ImageEntity> imageEntities = new ArrayList<>();

    @ManyToOne
    private LevelEntity level;

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
