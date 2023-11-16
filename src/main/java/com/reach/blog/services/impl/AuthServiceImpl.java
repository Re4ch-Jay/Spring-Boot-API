package com.reach.blog.services.impl;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.reach.blog.dto.LoginDTO;
import com.reach.blog.dto.RegisterDTO;
import com.reach.blog.exception.UserAlreadyExistHandler;
import com.reach.blog.models.User;
import com.reach.blog.models.Role;
import com.reach.blog.repository.UserRepository;
import com.reach.blog.security.JwtTokenProvider;
import com.reach.blog.services.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(
            JwtTokenProvider jwtTokenProvider,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDTO loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public void register(RegisterDTO registerDTO) {
        boolean usernameExists = userRepository.existsByUsername(registerDTO.getUsername());
        boolean emailExists = userRepository.existsByEmail(registerDTO.getEmail());

        if (!usernameExists && !emailExists) {

            User newUser = new User();
            newUser.setName(registerDTO.getName());
            newUser.setEmail(registerDTO.getEmail());
            newUser.setUsername(registerDTO.getUsername());
            newUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

            userRepository.save(newUser);
            return;
        } else {
            throw new UserAlreadyExistHandler();
        }
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }

}
