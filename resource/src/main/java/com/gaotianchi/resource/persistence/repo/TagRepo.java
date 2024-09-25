package com.gaotianchi.resource.persistence.repo;

import com.gaotianchi.resource.persistence.entity.TagEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepo extends JpaRepository<TagEntity, Long> {
    TagEntity findByName(String name);

    @Query("SELECT TagEntity.name FROM TagEntity ")
    List<String> findAllTagNames();

    // 根据文章数量从大到小排序分页查询标签
    Page<TagEntity> findByOrderByArticleCountDesc(Pageable pageable);
}
