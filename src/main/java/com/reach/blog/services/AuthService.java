package com.reach.blog.services;

import com.reach.blog.dto.LoginDTO;
import com.reach.blog.dto.RegisterDTO;

public interface AuthService {
    String login(LoginDTO loginDto);
    void register(RegisterDTO registerDTO);
    void logout();
}
