package com.gaotianchi.resource.web.service.cover;

import com.gaotianchi.resource.Utils;
import com.gaotianchi.resource.persistence.entity.SeriesCoverEntity;
import com.gaotianchi.resource.persistence.entity.SeriesEntity;
import com.gaotianchi.resource.persistence.entity.UserEntity;
import com.gaotianchi.resource.persistence.repo.SeriesCoverRepo;
import com.gaotianchi.resource.persistence.repo.SeriesRepo;
import com.gaotianchi.resource.web.response.PageSeriesCoverInfo;
import com.gaotianchi.resource.web.response.SeriesCoverInfo;
import com.gaotianchi.resource.web.service.belong.EntityBelongService;
import com.gaotianchi.resource.web.service.founder.EntityFounderService;
import com.gaotianchi.resource.web.service.storage.cover.SeriesCoverStorageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class SeriesCoverService implements SeriesCoverServiceInterface {
    private final EntityFounderService entityFounderService;
    private final SeriesCoverStorageService seriesCoverStorageService;
    private final SeriesCoverRepo seriesCoverRepo;
    private final EntityBelongService entityBelongService;
    private final SeriesRepo seriesRepo;

    public SeriesCoverService(EntityFounderService entityFounderService, SeriesCoverStorageService seriesCoverStorageService, SeriesCoverRepo seriesCoverRepo, EntityBelongService entityBelongService, SeriesRepo seriesRepo) {
        this.entityFounderService = entityFounderService;
        this.seriesCoverStorageService = seriesCoverStorageService;
        this.seriesCoverRepo = seriesCoverRepo;
        this.entityBelongService = entityBelongService;
        this.seriesRepo = seriesRepo;
    }

    @Override
    public SeriesCoverInfo newSeriesCover(String username, MultipartFile file) throws IOException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        String filename = Utils.generateUniqueFileName() + Utils.getFileExtension(file.getOriginalFilename());
        seriesCoverStorageService.save(filename, file);
        SeriesCoverEntity seriesCoverEntity = new SeriesCoverEntity();
        seriesCoverEntity.setFilename(filename);
        seriesCoverEntity.setCreationDatetime(OffsetDateTime.now());
        seriesCoverEntity.setUser(userEntity);
        // TODO: 设置链接
        return new SeriesCoverInfo(seriesCoverRepo.save(seriesCoverEntity));
    }

    @Override
    public void delete(String username, Long id) throws IOException {
        SeriesCoverEntity seriesCoverEntity = entityBelongService.seriesCoverBelongToUser(username, id);
        SeriesEntity seriesEntity = seriesCoverEntity.getSeries();
        seriesEntity.setCover(null);
        seriesRepo.save(seriesEntity);
        seriesCoverStorageService.delete(seriesCoverEntity.getFilename());
        seriesCoverRepo.delete(seriesCoverEntity);
    }

    @Override
    public SeriesCoverInfo getInfo(String username, Long id) {
        SeriesCoverEntity seriesCoverEntity = entityBelongService.seriesCoverBelongToUser(username, id);
        return new SeriesCoverInfo(seriesCoverEntity);
    }

    @Override
    public PageSeriesCoverInfo getPageInfo(String username, int page) {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        Pageable pageable = PageRequest.of(page, 10);
        Page<SeriesCoverEntity> seriesCoverEntityPage = seriesCoverRepo.findByUserOrderByCreationDatetimeDesc(userEntity, pageable);
        List<SeriesCoverInfo> seriesCoverInfoList = seriesCoverEntityPage.getContent().stream().map(SeriesCoverInfo::new).toList();
        return new PageSeriesCoverInfo(seriesCoverInfoList, seriesCoverEntityPage.getTotalPages(), page);
    }
}
