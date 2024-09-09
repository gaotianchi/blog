package com.gaotianchi.resource.persistence.repo;

import com.gaotianchi.resource.persistence.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepo extends JpaRepository<TagEntity, Long> {
    TagEntity findByName(String name);
}
