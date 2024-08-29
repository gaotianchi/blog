package com.gaotianchi.resourceservice.repo;

import com.gaotianchi.resourceservice.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepo extends JpaRepository<TagEntity, Long> {
}
