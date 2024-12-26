package com.example.thrive.user.service;

import com.example.thrive.user.repo.UserRepository;
import com.example.thrive.user.model.NGOModel;
import com.example.thrive.user.model.UserModel;
import com.example.thrive.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.thrive.user.model.request.RequestStatus.*;

@Service
public class AdminService {

    private final UserRepository userRepository;

    @Autowired
    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public List<UserModel> getAllUsers(UserRole userRole) {
        if (userRole == null)
            return userRepository.findAll();
        return userRepository.findAllByUserRole(userRole);
    }

    public String approveOrDisapproveNGO(String username, boolean approve) throws IllegalArgumentException{
        NGOModel ngoModel = checkIfNGOExists(username);
        if (approve)
            ngoModel.setRequestStatus(APPROVED);
        else
            ngoModel.setRequestStatus(DISAPPROVED);
        userRepository.save(ngoModel);
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

}