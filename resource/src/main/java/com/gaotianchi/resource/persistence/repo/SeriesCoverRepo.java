package com.gaotianchi.resource.persistence.repo;

import com.gaotianchi.resource.persistence.entity.SeriesCoverEntity;
import com.gaotianchi.resource.persistence.entity.SeriesEntity;
import com.gaotianchi.resource.persistence.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesCoverRepo extends JpaRepository<SeriesCoverEntity, Long> {
    Page<SeriesCoverEntity> findByUserOrderByCreationDatetimeDesc(UserEntity userEntity);

    SeriesCoverEntity findBySeries(SeriesEntity seriesEntity);
}
