package com.example.thrive.user.controller;

import com.example.thrive.user.model.UserModel;
import com.example.thrive.user.model.UserRole;
import com.example.thrive.user.model.dao.ResourceDao;
import com.example.thrive.user.model.resource.ResourceType;
import com.example.thrive.user.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users/get")
    public ResponseEntity<List<UserModel>> getAllUsers(
            @RequestParam(required = false) UserRole userRole
            ) {
        log.info("Getting users");
        return ResponseEntity.status(200).body(adminService.getAllUsers(userRole));
    }

    @GetMapping("/resources/get")
    public ResponseEntity<List<ResourceDao>> getAllResources(
            @RequestParam(required = false) UserRole userRole
    ) {
        log.info("Getting Resources");
        return ResponseEntity.status(200).body(adminService.getAllResources());
    }

    @PostMapping("/request/ngo/{username}")
    public ResponseEntity<String> registerNGO(
            @PathVariable String username,
            @RequestParam boolean approve
            ) {
        try {
            return ResponseEntity.status(200).body(adminService.approveOrDisapproveNGO(username, approve));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @GetMapping("/requests")
    public ResponseEntity<?> getAllRequests() {
        try {
            return ResponseEntity.status(200).body(adminService.getAllRequests());
        } catch (Exception e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

}
