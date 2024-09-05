package com.gaotianchi.resourceservice.service.userservice;

import com.gaotianchi.resourceservice.web.response.ArticleResponse;
import com.gaotianchi.resourceservice.web.response.SeriesResponse;
import com.gaotianchi.resourceservice.web.response.UserResponse;

import java.util.List;

public interface UserServiceInterface {
    UserResponse newUser(String penName, String email, String password);

    UserResponse updateInfo(String email, String penName);

    UserResponse updateAvatar(String email, Long imageId);

    UserResponse resetPassword(String email, String newPassword);

    UserResponse deregister(String email);

    UserResponse lockUser(Long userId);

    List<UserResponse> listUsers();

    List<ArticleResponse> listArticles(String email);

    List<SeriesResponse> listSeries(String email);

}
