package com.gaotianchi.oauth2service.service.userservice;

import com.gaotianchi.oauth2service.web.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserServiceInterface extends UserDetailsService {
    UserResponse newUser(String username, String password);

    UserResponse updateUsername(String username, String newUsername);

    UserResponse updatePassword(String username, String newPassword);

    UserResponse deregister(String username);

    UserResponse lockUser(Long userId);

    UserResponse unlockUser(Long userId);
}
