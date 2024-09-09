package com.gaotianchi.resource.persistence.repo;

import com.gaotianchi.resource.persistence.entity.CommentVoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentVoteRepo extends JpaRepository<CommentVoteEntity, Long> {
}
