package com.example.thrive.user.controller;

import com.example.thrive.user.model.UserModel;
import com.example.thrive.user.model.UserRole;
import com.example.thrive.user.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<UserModel>> getAllUsers(
            @RequestParam(required = false) UserRole userRole
            ) {
        return ResponseEntity.status(200).body(adminService.getAllUsers(userRole));
    }

    @PostMapping("/request/ngo/{username}")
    public ResponseEntity<String> registerNGO(
            @PathVariable String username,
            @RequestParam boolean approve
            ) {
        try {
            return ResponseEntity.status(201).body(adminService.approveOrDisapproveNGO(username, approve));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

}
