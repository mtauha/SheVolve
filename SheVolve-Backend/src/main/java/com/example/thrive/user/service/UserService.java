package com.example.thrive.user.service;

import com.example.thrive.config.security.jwt.JwtService;
import com.example.thrive.config.security.user.UserPrincipal;
import com.example.thrive.user.model.*;
import com.example.thrive.user.model.request.RequestStatus;
import com.example.thrive.user.repo.UserRepository;
import com.example.thrive.user.repo.request.JoinRequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final JoinRequestRepo joinRequestRepo;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, JoinRequestRepo joinRequestRepo, BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.joinRequestRepo = joinRequestRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String registerEntrepreneur(UserDao userDao) throws IllegalArgumentException{
        if (userRepository.findByUsername(userDao.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already in use");
        }
        if (userRepository.findByEmail(userDao.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }
        UserModel userModel = UserDao.toUserModel(userDao);
        userModel.setPassword(bCryptPasswordEncoder.encode(userDao.getPassword()));
        userModel = userRepository.save(userModel);
        return jwtService.generateToken(userDao.getUsername());
    }


    public String verifyLogin(String username, String password) throws BadCredentialsException {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(username);
        }
        throw new BadCredentialsException("Invalid username or password");
    }

    public UserDao validateJwtToken(String jwtToken) throws IllegalArgumentException {
        String username = jwtService.extractUsernameFromToken(jwtToken);
        Optional<UserModel> user = userRepository.findByUsername(username);
        if (user.isPresent())
            return UserDao.fromUserModel(user.get());
        throw new IllegalArgumentException("Invalid token");
    }

}
