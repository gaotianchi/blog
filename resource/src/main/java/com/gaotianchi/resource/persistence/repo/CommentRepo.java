package com.gaotianchi.resource.persistence.repo;

import com.gaotianchi.resource.persistence.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<CommentEntity, Long> {
}
