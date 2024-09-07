package com.gaotianchi.resourceservice.service.userservice;

import com.gaotianchi.resourceservice.web.response.ArticleResponse;
import com.gaotianchi.resourceservice.web.response.SeriesResponse;
import com.gaotianchi.resourceservice.web.response.UserResponse;

import java.util.List;

public interface UserServiceInterface {
    UserResponse newUser(String penName, String username);

    UserResponse updateInfo(String username, String penName);

    UserResponse updateAvatar(String username, Long imageId);

    List<UserResponse> listUsers();

    List<ArticleResponse> listArticles(String username);

    List<SeriesResponse> listSeries(String username);

}
