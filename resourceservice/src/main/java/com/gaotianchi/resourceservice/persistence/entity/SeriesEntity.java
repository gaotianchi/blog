package com.gaotianchi.resourceservice.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Setter
@Getter
public class SeriesEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private OffsetDateTime creationDatetime;

    @ManyToOne
    private ImageEntity cover;

    @OneToMany(mappedBy = "seriesEntity")
    private Collection<ArticleEntity> articleEntities = new ArrayList<>();
}
