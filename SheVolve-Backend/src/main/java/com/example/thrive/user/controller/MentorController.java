package com.example.thrive.user.controller;

import com.example.thrive.user.model.EntrepreneurModel;
import com.example.thrive.user.service.MentorService;
import com.google.api.services.storage.Storage;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @PostMapping("/resource/add/{mentorshipId}")
    ResponseEntity<String>
    addResource(
            @PathVariable int mentorshipId,
            @RequestParam String resourceName,
            @RequestParam String resourceDescription,
            @RequestPart MultipartFile resourceFile
    ) {
        try {
            return ResponseEntity.status(201).body(mentorService.addResource(mentorshipId, resourceName, resourceDescription, resourceFile));
        } catch (Exception e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @GetMapping("/ngos")
    ResponseEntity<List<String>> getAllNgos() {
        return ResponseEntity.status(200).body(mentorService.getAllNgoNames());
    }

    @GetMapping("/entrepreneurs")
    ResponseEntity<List<EntrepreneurModel>>  getEntrepreneursUnderMentor() {
        return ResponseEntity.status(200).body(mentorService.getAllEntrepreneursUnderAMentor());
    }

}
