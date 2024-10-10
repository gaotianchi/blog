package com.gaotianchi.resource.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Setter
@Getter
public class TagEntity {
    @Id
    @GeneratedValue
    // shallow data
    private Long id;
    private String name;
    private OffsetDateTime creationDatetime;
    private Integer articleCount = 0;

    // depth data
    @ManyToMany
    private Collection<ArticleEntity> articleList = new ArrayList<>();

    public void increaseArticleCount() {
        if (articleCount == null) articleCount = 0;
        articleCount += 1;
    }

    public void decreaseArticleCount() {
        articleCount -= 1;
    }
}
