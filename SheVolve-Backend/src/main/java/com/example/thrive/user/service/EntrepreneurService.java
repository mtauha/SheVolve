package com.example.thrive.user.service;

import com.example.thrive.config.security.user.UserPrincipal;
import com.example.thrive.user.model.EntrepreneurModel;
import com.example.thrive.user.model.request.JoinRequest;
import com.example.thrive.user.model.request.RequestStatus;
import com.example.thrive.user.repo.UserRepository;
import com.example.thrive.user.model.NGOModel;
import com.example.thrive.user.model.UserModel;
import com.example.thrive.user.repo.request.JoinRequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static com.example.thrive.user.model.request.RequestType.*;

@Service
public class EntrepreneurService {

    private final UserRepository userRepository;

    private final JoinRequestRepo joinRequestRepo;


    @Autowired
    public EntrepreneurService(UserRepository userRepository, JoinRequestRepo joinRequestRepo) {
        this.userRepository = userRepository;
        this.joinRequestRepo = joinRequestRepo;
    }

    public String sendJoinRequest(String username) {
        Optional<UserModel> userModel = userRepository.findByUsername(username);
        if (userModel.isPresent()) {
            NGOModel ngoModel = (NGOModel) userModel.get();
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            JoinRequest request = JoinRequest.builder()
                    .requester((EntrepreneurModel) (((UserPrincipal) principal).getUserModel()))
                    .responder(ngoModel)
                    .requestType(REQUEST_BY_ENTREPRENEUR_TO_NGO)
                    .build();
            joinRequestRepo.save(request);
            return "Request Sent";
        }
        throw new RuntimeException("NGO Not Found");
    }
}
