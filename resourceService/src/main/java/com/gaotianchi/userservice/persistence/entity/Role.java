package com.gaotianchi.userservice.persistence.entity;

import com.gaotianchi.userservice.enums.RoleType;
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
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
    @ManyToMany
    private Collection<Privilege> privileges;

    public Role(RoleType roleType)
    {
        super();
        this.roleType = roleType;
    }
}
