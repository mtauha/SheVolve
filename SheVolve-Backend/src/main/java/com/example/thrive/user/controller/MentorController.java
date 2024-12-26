package com.example.thrive.user.controller;

import com.example.thrive.user.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mentor")
@PreAuthorize("hasAuthority('MENTOR')")
public class MentorController {

    private final MentorService mentorService;

    @Autowired
    public MentorController(MentorService mentorService) {
        this.mentorService = mentorService;
    }

    @PostMapping("/request/join/{ngoUsername}")
    ResponseEntity<String> createNGOJoinRequest(@PathVariable String ngoUsername) {
        try {
            return ResponseEntity.status(200).body(mentorService.sendJoinRequest(ngoUsername));
        }
        catch (Exception e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

}
