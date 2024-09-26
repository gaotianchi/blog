package com.gaotianchi.resource.web.service.avatar;

import com.gaotianchi.resource.Utils;
import com.gaotianchi.resource.persistence.entity.AvatarEntity;
import com.gaotianchi.resource.persistence.entity.UserEntity;
import com.gaotianchi.resource.persistence.repo.AvatarRepo;
import com.gaotianchi.resource.persistence.repo.UserRepo;
import com.gaotianchi.resource.web.response.info.AvatarInfo;
import com.gaotianchi.resource.web.response.page.PageAvatarInfo;
import com.gaotianchi.resource.web.service.belong.EntityBelongService;
import com.gaotianchi.resource.web.service.founder.EntityFounderService;
import com.gaotianchi.resource.web.service.storage.avatar.AvatarStorageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class AvatarService implements AvatarServiceInterface {
    private final EntityFounderService entityFounderService;
    private final AvatarStorageService avatarStorageService;
    private final AvatarRepo avatarRepo;
    private final EntityBelongService entityBelongService;
    private final UserRepo userRepo;

    public AvatarService(EntityFounderService entityFounderService, AvatarStorageService avatarStorageService, AvatarRepo avatarRepo, EntityBelongService entityBelongService, UserRepo userRepo) {
        this.entityFounderService = entityFounderService;
        this.avatarStorageService = avatarStorageService;
        this.avatarRepo = avatarRepo;
        this.entityBelongService = entityBelongService;
        this.userRepo = userRepo;
    }

    @Override
    public AvatarInfo newAvatar(String username, MultipartFile file) throws IOException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        String filename = Utils.generateUniqueFileName() + Utils.getFileExtension(file.getOriginalFilename());
        avatarStorageService.save(filename, file);
        AvatarEntity avatarEntity = new AvatarEntity();
        avatarEntity.setCreationDatetime(OffsetDateTime.now());
        avatarEntity.setUser(userEntity);
        avatarEntity.setFilename(filename);
        // TODO: 设置连接
        return new AvatarInfo(avatarRepo.save(avatarEntity));
    }

    @Override
    public AvatarInfo getActiveInfo(String username) {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        AvatarEntity avatarEntity = avatarRepo.findFirstByUserAndActiveTrue(userEntity);
        return new AvatarInfo(avatarEntity);
    }

    @Override
    public PageAvatarInfo getPageInfo(String username, Integer page) {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        Pageable pageable = PageRequest.of(page, 30);
        Page<AvatarEntity> avatarEntityPage = avatarRepo.findByUserOrderByCreationDatetimeDesc(userEntity);
        List<AvatarInfo> avatarInfoList = avatarEntityPage.getContent().stream().map(AvatarInfo::new).toList();
        return new PageAvatarInfo(avatarInfoList, avatarEntityPage.getTotalPages(), page);
    }

    @Override
    public void delete(String username, Long id) throws IOException {
        AvatarEntity avatarEntity = entityBelongService.avatarBelongToUser(username, id);
        UserEntity userEntity = avatarEntity.getUser();
        userEntity.getAvatarList().remove(avatarEntity);
        userRepo.save(userEntity);
        avatarStorageService.delete(avatarEntity.getFilename());
        avatarRepo.delete(avatarEntity);
    }
}
