package com.example.thrive.user.repo;

import com.example.thrive.user.model.mentorship.MentorShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentorshipRepository extends JpaRepository<MentorShip, Integer> {
}
