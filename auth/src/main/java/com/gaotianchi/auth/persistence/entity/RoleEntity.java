package com.gaotianchi.auth.persistence.entity;

import com.gaotianchi.auth.persistence.enums.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Collection<UserEntity> users;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<PrivilegeEntity> privileges;

    public RoleEntity(RoleType roleType) {
        this.roleType = roleType;
    }
}
