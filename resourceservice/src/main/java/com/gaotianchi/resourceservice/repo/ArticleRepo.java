package com.gaotianchi.resourceservice.repo;

import com.gaotianchi.resourceservice.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepo extends JpaRepository<ArticleEntity, Long> {
}
