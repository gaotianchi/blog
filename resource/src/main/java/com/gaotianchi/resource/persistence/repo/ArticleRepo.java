package com.gaotianchi.resource.persistence.repo;

import com.gaotianchi.resource.persistence.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepo extends JpaRepository<ArticleEntity, Long> {
}
