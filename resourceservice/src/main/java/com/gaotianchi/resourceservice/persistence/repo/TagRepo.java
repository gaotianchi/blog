package com.gaotianchi.resourceservice.persistence.repo;

import com.gaotianchi.resourceservice.persistence.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepo extends JpaRepository<TagEntity, Long> {
    TagEntity findByName(String name);
}
