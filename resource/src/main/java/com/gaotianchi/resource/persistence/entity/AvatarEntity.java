package com.gaotianchi.resource.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Setter
@Getter
public class AvatarEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String filename;
    private String url;
    private boolean active;
    private OffsetDateTime creationDatetime;

    @ManyToOne
    private UserEntity user;
}
