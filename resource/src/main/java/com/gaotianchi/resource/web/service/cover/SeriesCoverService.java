package com.gaotianchi.resource.web.service.cover;

import com.gaotianchi.resource.Utils;
import com.gaotianchi.resource.persistence.entity.SeriesCoverEntity;
import com.gaotianchi.resource.persistence.entity.SeriesEntity;
import com.gaotianchi.resource.persistence.entity.UserEntity;
import com.gaotianchi.resource.persistence.repo.SeriesCoverRepo;
import com.gaotianchi.resource.persistence.repo.SeriesRepo;
import com.gaotianchi.resource.web.response.info.SeriesCoverInfo;
import com.gaotianchi.resource.web.service.belong.EntityBelongService;
import com.gaotianchi.resource.web.service.founder.EntityFounderService;
import com.gaotianchi.resource.web.service.storage.cover.SeriesCoverStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.OffsetDateTime;

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
        seriesCoverEntity.setUrl("http://localhost:8090/images/series-cover/" + filename);
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
    public SeriesCoverInfo getInfo(Long id) {
        SeriesCoverEntity seriesCoverEntity = entityFounderService.getSeriesCoverOrNotFound(id);
        return new SeriesCoverInfo(seriesCoverEntity);
    }
}
