package com.gaotianchi.oauth2service.persistence.entity;

import com.gaotianchi.oauth2service.persistence.enums.PrivilegeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class PrivilegeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private PrivilegeType privilegeType;

    @ManyToMany(mappedBy = "privileges", fetch = FetchType.EAGER)
    private Collection<RoleEntity> roles;

    public PrivilegeEntity(PrivilegeType privilegeType) {
        this.privilegeType = privilegeType;
    }
}
