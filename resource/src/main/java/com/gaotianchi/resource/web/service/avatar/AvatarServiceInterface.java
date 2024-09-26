package com.gaotianchi.resource.web.service.avatar;

import com.gaotianchi.resource.web.response.info.AvatarInfo;
import com.gaotianchi.resource.web.response.page.PageAvatarInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AvatarServiceInterface {
    AvatarInfo newAvatar(String username, MultipartFile file) throws IOException;

    AvatarInfo getActiveInfo(String username);

    PageAvatarInfo getPageInfo(String username, Integer page);

    void delete(String username, Long id) throws IOException;
}
