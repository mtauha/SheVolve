package com.example.thrive.user.service;

import com.example.thrive.config.security.jwt.JwtService;
import com.example.thrive.user.model.*;
import com.example.thrive.user.model.dao.UserDao;
import com.example.thrive.user.model.request.JoinRequest;
import com.example.thrive.user.model.request.RequestType;
import com.example.thrive.user.repo.UserRepository;
import com.example.thrive.user.repo.JoinRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final JoinRequestRepository joinRequestRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, JoinRequestRepository joinRequestRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.joinRequestRepository = joinRequestRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String registerUser(UserDao userDao) throws IllegalArgumentException{
        if (userRepository.findByUsername(userDao.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already in use");
        }
        if (userRepository.findByEmail(userDao.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }
        UserModel userModel = UserDao.toUserModel(userDao);
        userModel.setPassword(bCryptPasswordEncoder.encode(userDao.getPassword()));
        userModel = userRepository.save(userModel);
        sendRequestToAdmin(userModel);
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

    private void sendRequestToAdmin(UserModel userModel) {
        try {
            NGOModel ngoModel = (NGOModel) userModel;
            JoinRequest joinRequest = JoinRequest.builder()
                    .requestType(RequestType.REQUEST_BY_NGO_TO_ADMIN)
                    .requester(ngoModel)
                    .request("I want to create my ngo for social work")
                    .responder(userRepository.findByUsername("admin").get())
                    .build();
            joinRequestRepository.save(joinRequest);
        } catch (Exception ignored) {
        }
    }

}
