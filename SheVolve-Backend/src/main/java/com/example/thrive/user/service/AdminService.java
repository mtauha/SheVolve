package com.example.thrive.user.service;

import com.example.thrive.user.model.dao.AdminRequestDao;
import com.example.thrive.user.model.dao.ResourceDao;
import com.example.thrive.user.model.request.JoinRequest;
import com.example.thrive.user.model.request.RequestType;
import com.example.thrive.user.model.resource.Resource;
import com.example.thrive.user.repo.ResourceRepository;
import com.example.thrive.user.repo.UserRepository;
import com.example.thrive.user.model.NGOModel;
import com.example.thrive.user.model.UserModel;
import com.example.thrive.user.model.UserRole;
import com.example.thrive.user.repo.JoinRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.thrive.user.model.request.RequestStatus.*;

@Service
public class AdminService {

    private final UserRepository userRepository;

    private final JoinRequestRepository joinRequestRepository;

    private final ResourceRepository resourceRepository;

    @Autowired
    public AdminService(UserRepository userRepository, JoinRequestRepository joinRequestRepository, ResourceRepository resourceRepository) {
        this.userRepository = userRepository;
        this.joinRequestRepository = joinRequestRepository;
        this.resourceRepository = resourceRepository;
    }



    public List<UserModel> getAllUsers(UserRole userRole) {
        if (userRole == null)
            return userRepository.findAll();
        return userRepository.findAllByUserRole(userRole);
    }

    public String approveOrDisapproveNGO(String username, boolean approve) throws IllegalArgumentException{
        NGOModel ngoModel = checkIfNGOExists(username);
        JoinRequest joinRequest = getJoinRequestByNgoUsername(username);
        if (approve) {
            ngoModel.setRequestStatus(APPROVED);
            joinRequest.setRequestStatus(APPROVED);
        }
        else {
            ngoModel.setRequestStatus(DISAPPROVED);
            joinRequest.setRequestStatus(DISAPPROVED);
        }

        userRepository.save(ngoModel);
        joinRequestRepository.save(joinRequest);

        return String.format("NGO %s request %s", username, ngoModel.getRequestStatus());
    }

    public NGOModel checkIfNGOExists(String username) {
        Optional<UserModel> userModel = userRepository.findByUsername(username);
        if (userModel.isEmpty()) {
            throw new IllegalArgumentException("NGO with given username does not exist");
        }
        try {
            NGOModel ngoModel = (NGOModel) userModel.get();
            return ngoModel;
        }catch (ClassCastException e){
            throw new IllegalArgumentException("Provided username is not a NGO");
        }
    }

    public List<ResourceDao> getAllResources() {
        List<Resource> resources = resourceRepository.findAll();
        List<ResourceDao> resourceDaos = new ArrayList<>();
        resources.forEach(resource -> {
            resourceDaos.add(ResourceDao.fromResource(resource));
        });
        return resourceDaos;
    }

    public List<AdminRequestDao> getAllRequests() {
        List<JoinRequest> joinRequests = joinRequestRepository.findAllByRequestType(RequestType.REQUEST_BY_NGO_TO_ADMIN);

        List<AdminRequestDao> adminRequestDaos = new ArrayList<>();
        joinRequests.forEach(joinRequest -> {
            adminRequestDaos.add(AdminRequestDao.fromJoinRequest(joinRequest));
        });

        return adminRequestDaos;
    }

    public JoinRequest getJoinRequestByNgoUsername(String ngoUsername) {
        Optional<UserModel> userModel = userRepository.findByUsername(ngoUsername);
        if (userModel.isEmpty())
            throw new IllegalArgumentException("NGO with given username does not exist");
        try {
            NGOModel ngoModel = (NGOModel) userModel.get();
            Optional<JoinRequest> joinRequest = joinRequestRepository.findByRequester(ngoModel);
            if (joinRequest.isEmpty())
                throw new IllegalArgumentException("Given request is not of provided NGO");
            return joinRequest.get();
        } catch (ClassCastException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }


}