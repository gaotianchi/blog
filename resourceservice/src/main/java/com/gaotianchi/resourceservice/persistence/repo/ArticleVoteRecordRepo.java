package com.gaotianchi.resourceservice.persistence.repo;

import com.gaotianchi.resourceservice.persistence.entity.ArticleVoteRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleVoteRecordRepo extends JpaRepository<ArticleVoteRecordEntity, Long> {
}
