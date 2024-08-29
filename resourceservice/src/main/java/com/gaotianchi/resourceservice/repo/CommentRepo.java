package com.gaotianchi.resourceservice.repo;

import com.gaotianchi.resourceservice.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<CommentEntity, Long> {
}
