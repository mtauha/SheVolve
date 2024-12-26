package com.example.thrive.user.repo.request;

import com.example.thrive.user.model.request.JoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinRequestRepo extends JpaRepository<JoinRequest, Integer> {
}
