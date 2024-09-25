package com.gaotianchi.resource.web.service.avatar;

import com.gaotianchi.resource.web.response.AvatarInfo;
import com.gaotianchi.resource.web.response.PageAvatarInfo;
import org.springframework.web.multipart.MultipartFile;

public interface AvatarServiceInterface {
    AvatarInfo newAvatar(String username, MultipartFile file);

    AvatarInfo getActiveInfo(String username);

    PageAvatarInfo getPageInfo(String username, Integer page);

    void delete(String username, Long id);
}
