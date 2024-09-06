package com.gaotianchi.oauth2service.service.userservice;

import com.gaotianchi.oauth2service.web.request.NewUserRequest;
import com.gaotianchi.oauth2service.web.request.UpdatePasswordRequest;
import com.gaotianchi.oauth2service.web.request.UpdateUsernameRequest;
import com.gaotianchi.oauth2service.web.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserServiceInterface extends UserDetailsService {
    UserResponse newUser(NewUserRequest newUserRequest);

    UserResponse updateUsername(UpdateUsernameRequest updateUsernameRequest);

    UserResponse updatePassword(UpdatePasswordRequest updatePasswordRequest);

    UserResponse deregister(String username);

    UserResponse lockUser(Long userId);

    UserResponse unlockUser(Long userId);
}
