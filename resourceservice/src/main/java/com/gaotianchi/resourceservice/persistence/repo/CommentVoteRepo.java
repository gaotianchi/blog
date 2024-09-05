package com.gaotianchi.resourceservice.persistence.repo;

import com.gaotianchi.resourceservice.persistence.entity.CommentVoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentVoteRepo extends JpaRepository<CommentVoteEntity, Long> {
}
