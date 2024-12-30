package com.example.thrive.user.controller;

import com.example.thrive.user.model.MentorModel;
import com.example.thrive.user.service.NgoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/ngo")
@PreAuthorize("hasAuthority('NGO')")
public class NgoController {

    private final NgoService ngoService;

    @Autowired
    public NgoController(NgoService ngoService) {
        this.ngoService = ngoService;
    }

    @PostMapping("/request/send")
    public ResponseEntity<String> sendNgoCreationRequest() {
        try {
            return ResponseEntity.status(200).body(ngoService.sendNgoCreationRequestToAdmin());
        } catch (Exception e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @PostMapping("/request/mentor/{requestId}")
    public ResponseEntity<String> reactOnMentorJoinRequest(@PathVariable int requestId, @RequestParam boolean approve) {
        try {
            return ResponseEntity.status(200).body(ngoService.acceptOrRejectMentorJoinRequest(requestId, approve));
        }
        catch (Exception e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @PostMapping("/request/entrepreneur/{requestId}")
    public ResponseEntity<String> reactOnEntrepreneurJoinRequest(@PathVariable int requestId, @RequestParam boolean approve, @RequestParam String mentorName) {
        try {
            return ResponseEntity.status(200).body(ngoService.acceptOrRejectEntrepreneurJoinRequest(mentorName, requestId, approve));
        }
        catch (Exception e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @GetMapping("/mentors")
    public ResponseEntity<?> getMentors() {
        try {
            return ResponseEntity.status(200).body(ngoService.getMentors());
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }
}
