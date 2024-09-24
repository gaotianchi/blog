package com.gaotianchi.resource.persistence.repo;

import com.gaotianchi.resource.persistence.entity.ArticleEntity;
import com.gaotianchi.resource.persistence.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepo extends JpaRepository<ArticleEntity, Long> {
    Page<ArticleEntity> findByAuthorOrderByCreationDatetimeDesc(UserEntity userEntity, Pageable pageable);
}
