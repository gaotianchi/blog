package com.gaotianchi.resource.web.service.avatar;

import com.gaotianchi.resource.web.response.AvatarInfo;
import com.gaotianchi.resource.web.response.PageAvatarInfo;
import org.springframework.web.multipart.MultipartFile;

public class AvatarService implements AvatarServiceInterface {
    @Override
    public AvatarInfo newAvatar(String username, MultipartFile file) {
        return null;
    }

    @Override
    public AvatarInfo getActiveInfo(String username) {
        return null;
    }

    @Override
    public PageAvatarInfo getPageInfo(String username, Integer page) {
        return null;
    }

    @Override
    public void delete(String username, Long id) {

    }
}
