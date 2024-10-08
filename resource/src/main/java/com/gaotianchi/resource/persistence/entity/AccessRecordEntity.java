package com.gaotianchi.resource.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
public class AccessRecordEntity {
    @Id
    @GeneratedValue
    // shallow data
    private Long id;
    private OffsetDateTime accessTime;
    private String accessPath;
    private String ipAddress;

    // depth data
    @ManyToOne
    private UserEntity user;
}
