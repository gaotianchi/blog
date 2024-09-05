package com.gaotianchi.resourceservice.persistence.repo;

import com.gaotianchi.resourceservice.persistence.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<CommentEntity, Long> {
}
