package com.example.thrive.user.controller;

import com.example.thrive.user.service.EntrepreneurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/entrepreneur")
@PreAuthorize("hasAuthority('ENTREPRENEUR')")
public class EntrepreneurController {

    private final EntrepreneurService entrepreneurService;

    @Autowired
    public EntrepreneurController(EntrepreneurService entrepreneurService) {
        this.entrepreneurService = entrepreneurService;
    }

    @PostMapping("/request/join/{ngoUsername}")
    ResponseEntity<String> createNGOJoinRequest(@PathVariable String ngoUsername) {
        try {
            return ResponseEntity.status(200).body(entrepreneurService.sendJoinRequest(ngoUsername));
        }
        catch (Exception e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

}
