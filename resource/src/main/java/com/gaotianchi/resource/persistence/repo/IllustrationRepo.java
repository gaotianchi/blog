package com.gaotianchi.resource.persistence.repo;

import com.gaotianchi.resource.persistence.entity.ArticleEntity;
import com.gaotianchi.resource.persistence.entity.IllustrationEntity;
import com.gaotianchi.resource.persistence.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IllustrationRepo extends JpaRepository<IllustrationEntity, Long> {

    Page<IllustrationEntity> findByUserOrderByCreationDatetimeDesc(UserEntity userEntity, Pageable pageable);

    Page<IllustrationEntity> findByArticleListContaining(ArticleEntity articleEntity, Pageable pageable);
}
