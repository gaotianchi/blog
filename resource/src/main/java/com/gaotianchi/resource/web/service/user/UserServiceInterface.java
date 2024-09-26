package com.gaotianchi.resource.web.service.user;

import com.gaotianchi.resource.web.response.info.AvatarInfo;
import com.gaotianchi.resource.web.response.info.UserInfo;

import java.util.TimeZone;

public interface UserServiceInterface {
    UserInfo newUser(String username, String penName, String profile, TimeZone timeZone) throws Exception;

    void deleteUser(String username);

    void updateInfo(String username, String newPenName, String newProfile, TimeZone newTimeZone);

    UserInfo getInfo(String username);

    AvatarInfo setAvatar(String username, Long avatarId);

    AvatarInfo updateAvatar(String username, Long newAvatarId);

    void removeAvatar(String username, Long avatarId);
}
