package com.example.thrive.user.service;

import com.example.thrive.config.security.user.UserPrincipal;
import com.example.thrive.user.model.NGOModel;
import com.example.thrive.user.model.request.JoinRequest;
import com.example.thrive.user.model.request.RequestStatus;
import com.example.thrive.user.repo.UserRepository;
import com.example.thrive.user.repo.request.JoinRequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.thrive.user.model.request.RequestType.*;

@Service
public class NgoService {

    private final UserRepository userRepository;

    private final JoinRequestRepo joinRequestRepo;


    @Autowired
    public NgoService(UserRepository userRepository, JoinRequestRepo joinRequestRepo) {
        this.userRepository = userRepository;
        this.joinRequestRepo = joinRequestRepo;
    }

    public String acceptOrRejectMentorJoinRequest(int requestId, boolean approve) {
        JoinRequest joinRequest = checkIfRequestExists(requestId);
        checkIfRequestIsOfMentor(joinRequest);
        if (approve)
            joinRequest.setRequestStatus(RequestStatus.APPROVED);
        else
            joinRequest.setRequestStatus(RequestStatus.DISAPPROVED);
        joinRequestRepo.save(joinRequest);
        return String.format("Joining request of '%s' approved %s", joinRequest.getRequester().getUsername(), joinRequest.getRequestStatus().toString().toLowerCase());
    }

    public String acceptOrRejectEntrepreneurJoinRequest(int requestId, boolean approve) {
        JoinRequest joinRequest = checkIfRequestExists(requestId);
        checkIfRequestIsOfEntrepreneur(joinRequest);
        if (approve)
            joinRequest.setRequestStatus(RequestStatus.APPROVED);
        else
            joinRequest.setRequestStatus(RequestStatus.DISAPPROVED);
        joinRequestRepo.save(joinRequest);
        return String.format("Joining request of '%s' approved %s", joinRequest.getRequester().getUsername(), joinRequest.getRequestStatus().toString().toLowerCase());
    }

    private JoinRequest checkIfRequestExists(int requestId) {
        Optional<JoinRequest> joinRequest = joinRequestRepo.findById(requestId);
        if (joinRequest.isEmpty()) {
            throw new IllegalArgumentException("Request with given Id does not exists");
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        NGOModel currentUser = ((NGOModel) ((UserPrincipal) principal).getUserModel());
        if (currentUser.getUserId() != joinRequest.get().getResponder().getUserId())
            throw new IllegalArgumentException("NGO can only accept it own requests");
        return joinRequest.get();
    }

    private void checkIfRequestIsOfMentor(JoinRequest joinRequest) {
        if (joinRequest.getRequestType() != REQUEST_BY_MENTOR_TO_NGO)
            throw new IllegalArgumentException("Provided request id is not of a mentor");
    }

    private void checkIfRequestIsOfEntrepreneur(JoinRequest joinRequest) {
        if (joinRequest.getRequestType() != REQUEST_BY_ENTREPRENEUR_TO_NGO)
            throw new IllegalArgumentException("Provided request id is not of an entrepreneur");
    }
}
