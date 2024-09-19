package com.gaotianchi.resource.persistence.repo;

import com.gaotianchi.resource.persistence.entity.ImageEntity;
import com.gaotianchi.resource.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepo extends JpaRepository<ImageEntity, Long> {
    List<ImageEntity> findByForArticleIsTrueAndUserOrderByCreationDatetimeDesc(UserEntity userEntity);

    List<ImageEntity> findByForAvatarIsTrueAndUserOrderByCreationDatetimeDesc(UserEntity userEntity);

    List<ImageEntity> findByForSeriesIsTrueAndUserOrderByCreationDatetimeDesc(UserEntity userEntity);
}
