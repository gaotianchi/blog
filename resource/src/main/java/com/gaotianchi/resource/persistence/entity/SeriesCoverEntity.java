package com.gaotianchi.resource.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Setter
@Getter
public class SeriesCoverEntity {
    @Id
    @GeneratedValue
    // shallow data
    private Long id;
    private String filename;
    private String url;
    private OffsetDateTime creationDatetime;

    // depth
    @ManyToOne
    private UserEntity user;

    @OneToOne
    private SeriesEntity series;
}
