package com.gaotianchi.resource.persistence.repo;

import com.gaotianchi.resource.persistence.entity.IllustrationEntity;
import com.gaotianchi.resource.persistence.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IllustrationRepo extends JpaRepository<IllustrationEntity, Long> {

    // 根据时间排序获取指定用户的插图
    Page<IllustrationEntity> findByUserOrderByCreationDatetimeDesc(UserEntity userEntity);

    Page<IllustrationEntity> findByUserOrderByCreationDatetimeAsc(UserEntity userEntity);

    Page<IllustrationEntity> findByUserOrderByUpdateDatetimeDesc(UserEntity userEntity);

    Page<IllustrationEntity> findByUserOrderByUpdateDatetimeAsc(UserEntity userEntity);

}
