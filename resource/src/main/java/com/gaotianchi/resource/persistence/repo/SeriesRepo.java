package com.gaotianchi.resource.persistence.repo;

import com.gaotianchi.resource.persistence.entity.SeriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeriesRepo extends JpaRepository<SeriesEntity, Long> {
}
