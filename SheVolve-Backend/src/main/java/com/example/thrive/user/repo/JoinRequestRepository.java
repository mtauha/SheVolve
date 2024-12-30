package com.example.thrive.user.repo;

import com.example.thrive.user.model.UserModel;
import com.example.thrive.user.model.request.JoinRequest;
import com.example.thrive.user.model.request.RequestType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JoinRequestRepository extends JpaRepository<JoinRequest, Integer> {

    List<JoinRequest> findAllByRequestType(RequestType requestType);

    Optional<JoinRequest> findByRequester(UserModel userModel);
}
