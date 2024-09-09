package com.gaotianchi.resource.persistence.repo;

import com.gaotianchi.resource.persistence.entity.CommentVoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentVoteRepo extends JpaRepository<CommentVoteEntity, Long> {
}
