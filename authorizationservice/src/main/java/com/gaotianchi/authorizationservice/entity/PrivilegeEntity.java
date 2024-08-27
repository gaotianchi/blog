package com.gaotianchi.authorizationservice.entity;
import com.gaotianchi.authorizationservice.enums.PrivilegeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrivilegeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private PrivilegeType privilegeType;

    @ManyToMany(mappedBy = "privileges")
    private Collection<RoleEntity> roles;

    public PrivilegeEntity(PrivilegeType privilegeType) {
        super();
        this.privilegeType = privilegeType;
    }
}
