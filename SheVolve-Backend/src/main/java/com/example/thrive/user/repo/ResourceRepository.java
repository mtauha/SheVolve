package com.example.thrive.user.repo;

import com.example.thrive.user.model.resource.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {
}
