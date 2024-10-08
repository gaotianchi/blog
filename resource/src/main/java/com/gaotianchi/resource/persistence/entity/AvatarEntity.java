package com.gaotianchi.resource.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Setter
@Getter
public class AvatarEntity {
    @Id
    @GeneratedValue
    // shallow data
    private Long id;
    private String filename;
    private String url;
    private OffsetDateTime creationDatetime;

    // depth data
    @OneToOne
    private UserEntity user;
}
