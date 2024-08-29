package com.gaotianchi.resourceservice.entity;

import com.gaotianchi.resourceservice.enums.AvatarType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AvatarEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private AvatarType avatarType;
    private String filename;

    @OneToOne
    private UserEntity userEntity;
}
