package com.gaotianchi.resource.web.service.user;

import com.gaotianchi.resource.web.response.info.UserInfo;

import java.util.TimeZone;

public interface UserServiceInterface {
    UserInfo newUser(String username, String penName, String profile, TimeZone timeZone) throws Exception;

    void deleteUser(String username);

    void updateInfo(String username, String newPenName, String newProfile, TimeZone newTimeZone);

    UserInfo getPublicInfo(Long id);

    UserInfo getPrivateInfo(String username);
}
