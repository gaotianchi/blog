package com.gaotianchi.oauth2service.service.userservice;

import com.gaotianchi.oauth2service.web.request.NewUserRequest;
import com.gaotianchi.oauth2service.web.request.UpdatePasswordRequest;
import com.gaotianchi.oauth2service.web.request.UpdateUsernameRequest;
import com.gaotianchi.oauth2service.web.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserService implements UserServiceInterface {

    @Override
    public UserResponse newUser(NewUserRequest newUserRequest) {
        return null;
    }

    @Override
    public UserResponse updateUsername(UpdateUsernameRequest updateUsernameRequest) {
        return null;
    }

    @Override
    public UserResponse updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        return null;
    }

    @Override
    public UserResponse deregister(String username) {
        return null;
    }

    @Override
    public UserResponse lockUser(Long userId) {
        return null;
    }

    @Override
    public UserResponse unlockUser(Long userId) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
