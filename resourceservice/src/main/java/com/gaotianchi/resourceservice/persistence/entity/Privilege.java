package com.gaotianchi.userservice.persistence.entity;

import com.gaotianchi.userservice.enums.PrivilegeType;
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
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private PrivilegeType privilegeType;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    public Privilege(PrivilegeType privilegeType) {
        super();
        this.privilegeType = privilegeType;
    }
}
