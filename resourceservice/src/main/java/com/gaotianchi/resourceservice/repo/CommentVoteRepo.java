package com.gaotianchi.resourceservice.repo;

import com.gaotianchi.resourceservice.entity.CommentVoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentVoteRepo extends JpaRepository<CommentVoteEntity, Long> {
}
