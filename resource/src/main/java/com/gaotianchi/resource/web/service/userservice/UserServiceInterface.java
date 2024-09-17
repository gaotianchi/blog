package com.gaotianchi.resource.web.service.userservice;

import com.gaotianchi.resource.web.response.ArticleResponse;
import com.gaotianchi.resource.web.response.SeriesResponse;
import com.gaotianchi.resource.web.response.UserResponse;

import java.util.List;

public interface UserServiceInterface {
    UserResponse newUser(String penName, String username);

    UserResponse updateInfo(String username, String penName, String profile, Long avatarId, String timezone);

    UserResponse updateAvatar(String username, Long imageId);

    List<UserResponse> listUsers();

    List<ArticleResponse> listArticles(String username);

    List<SeriesResponse> listSeries(String username);

    UserResponse getUserInfo(String username);

}
