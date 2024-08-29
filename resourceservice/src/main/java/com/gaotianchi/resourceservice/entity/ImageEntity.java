package com.gaotianchi.resourceservice.entity;

import com.gaotianchi.resourceservice.enums.ImageType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ImageEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String filepath;
    private String alt;
    private String filename;
    @Enumerated(EnumType.STRING)
    private ImageType imageType;


    @ManyToOne
    private ArticleEntity article;
}
