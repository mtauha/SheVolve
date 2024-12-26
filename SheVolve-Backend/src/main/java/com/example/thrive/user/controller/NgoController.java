package com.example.thrive.user.controller;

import com.example.thrive.user.service.NgoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ngo")
@PreAuthorize("hasAuthority('NGO')")
public class NgoController {

    private final NgoService ngoService;

    @Autowired
    public NgoController(NgoService ngoService) {
        this.ngoService = ngoService;
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
    public ResponseEntity<String> reactOnEntrepreneurJoinRequest(@PathVariable int requestId, @RequestParam boolean approve) {
        try {
            return ResponseEntity.status(200).body(ngoService.acceptOrRejectEntrepreneurJoinRequest(requestId, approve));
        }
        catch (Exception e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }
}
