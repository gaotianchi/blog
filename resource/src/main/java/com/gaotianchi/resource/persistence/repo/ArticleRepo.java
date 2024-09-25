package com.gaotianchi.resource.persistence.repo;

import com.gaotianchi.resource.persistence.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepo extends JpaRepository<ArticleEntity, Long> {

    // 根据时间排序分页查询指定用户的文章实体
    Page<ArticleEntity> findByUserOrderByCreationDatetimeDesc(UserEntity userEntity, Pageable pageable);

    Page<ArticleEntity> findByUserOrderByCreationDatetimeAsc(UserEntity userEntity, Pageable pageable);

    Page<ArticleEntity> findByUserOrderByUpdateDatetimeDesc(UserEntity userEntity, Pageable pageable);

    Page<ArticleEntity> findByUserOrderByUpdateDatetimeAsc(UserEntity userEntity, Pageable pageable);

    // 分页查询指定系列下的文章实体
    Page<ArticleEntity> findBySeriesOrderByCreationDatetimeDesc(SeriesEntity seriesEntity, Pageable pageable);

    Page<ArticleEntity> findBySeriesOrderByCreationDatetimeAsc(SeriesEntity seriesEntity, Pageable pageable);

    Page<ArticleEntity> findBySeriesOrderByUpdateDatetimeDesc(SeriesEntity seriesEntity, Pageable pageable);

    Page<ArticleEntity> findBySeriesOrderByUpdateDatetimeAsc(SeriesEntity seriesEntity, Pageable pageable);

    // 根据标签分页查询文章实体
    Page<ArticleEntity> findByTagListContainingOrderByCreationDatetimeDesc(TagEntity tagEntity, Pageable pageable);

    Page<ArticleEntity> findByTagListContainingOrderByCreationDatetimeAsc(TagEntity tagEntity, Pageable pageable);

    Page<ArticleEntity> findByTagListContainingOrderByUpdateDatetimeDesc(TagEntity tagEntity, Pageable pageable);

    Page<ArticleEntity> findByTagListContainingOrderByUpdateDatetimeAsc(TagEntity tagEntity, Pageable pageable);

    // 根据插图分页查询文章实体
    Page<ArticleEntity> findByIllustrationListContainingOrderByCreationDatetimeDesc(IllustrationEntity illustrationEntity, Pageable pageable);

    Page<ArticleEntity> findByIllustrationListContainingOrderByCreationDatetimeAsc(IllustrationEntity illustrationEntity, Pageable pageable);

    Page<ArticleEntity> findByIllustrationListContainingOrderByUpdateDatetimeDesc(IllustrationEntity illustrationEntity, Pageable pageable);

    Page<ArticleEntity> findByIllustrationListContainingOrderByUpdateDatetimeAsc(IllustrationEntity illustrationEntity, Pageable pageable);
}
