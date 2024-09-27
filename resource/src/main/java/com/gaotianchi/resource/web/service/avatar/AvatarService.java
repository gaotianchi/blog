package com.gaotianchi.resource.web.service.avatar;

import com.gaotianchi.resource.Utils;
import com.gaotianchi.resource.persistence.entity.AvatarEntity;
import com.gaotianchi.resource.persistence.entity.UserEntity;
import com.gaotianchi.resource.persistence.repo.AvatarRepo;
import com.gaotianchi.resource.web.response.info.AvatarInfo;
import com.gaotianchi.resource.web.service.founder.EntityFounderService;
import com.gaotianchi.resource.web.service.storage.avatar.AvatarStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.OffsetDateTime;

@Service
public class AvatarService implements AvatarServiceInterface {
    private final EntityFounderService entityFounderService;
    private final AvatarStorageService avatarStorageService;
    private final AvatarRepo avatarRepo;

    public AvatarService(EntityFounderService entityFounderService, AvatarStorageService avatarStorageService, AvatarRepo avatarRepo) {
        this.entityFounderService = entityFounderService;
        this.avatarStorageService = avatarStorageService;
        this.avatarRepo = avatarRepo;
    }

    @Override
    public AvatarInfo newAvatar(String username, MultipartFile file) throws IOException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        _deleteAvatar(userEntity);
        String filename = Utils.generateUniqueFileName() + Utils.getFileExtension(file.getOriginalFilename());
        avatarStorageService.save(filename, file);
        AvatarEntity avatarEntity = new AvatarEntity();
        avatarEntity.setCreationDatetime(OffsetDateTime.now());
        avatarEntity.setUser(userEntity);
        avatarEntity.setFilename(filename);
        avatarEntity.setUrl("http://localhost:8090/images/avatar/" + filename);
        return new AvatarInfo(avatarRepo.save(avatarEntity));
    }

    @Override
    public void deleteAvatar(String username) throws IOException {
        UserEntity userEntity = entityFounderService.getUserOrNotFound(username);
        _deleteAvatar(userEntity);
    }

    @Override
    public AvatarInfo getInfo(Long id) {
        AvatarEntity avatarEntity = entityFounderService.getAvatarOrNotFound(id);
        return new AvatarInfo(avatarEntity);
    }

    private void _deleteAvatar(UserEntity userEntity) throws IOException {
        AvatarEntity avatarEntity = userEntity.getAvatar();
        if (avatarEntity != null) {
            avatarStorageService.delete(avatarEntity.getFilename());
            avatarRepo.delete(avatarEntity);
        }
    }
}
