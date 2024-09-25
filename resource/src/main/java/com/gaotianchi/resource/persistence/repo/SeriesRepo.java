package com.gaotianchi.resource.persistence.repo;

import com.gaotianchi.resource.persistence.entity.SeriesEntity;
import com.gaotianchi.resource.persistence.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeriesRepo extends JpaRepository<SeriesEntity, Long> {
    Page<SeriesEntity> findByUserOrderByCreationDatetimeDesc(UserEntity userEntity, Pageable pageable);
}
