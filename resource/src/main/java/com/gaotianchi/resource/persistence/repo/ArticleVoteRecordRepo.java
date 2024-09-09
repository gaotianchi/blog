package com.gaotianchi.resource.persistence.repo;

import com.gaotianchi.resource.persistence.entity.ArticleVoteRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleVoteRecordRepo extends JpaRepository<ArticleVoteRecordEntity, Long> {
}
