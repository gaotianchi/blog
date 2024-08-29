package com.gaotianchi.resourceservice.repo;

import com.gaotianchi.resourceservice.entity.ArticleVoteRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleVoteRecordRepo extends JpaRepository<ArticleVoteRecordEntity, Long> {
}
