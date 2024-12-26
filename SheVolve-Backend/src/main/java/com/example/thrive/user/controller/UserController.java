package com.example.thrive.user.controller;

import com.example.thrive.user.model.EntrepreneurModel;
import com.example.thrive.user.model.UserDao;
import com.example.thrive.user.model.UserModel;
import com.example.thrive.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> registerEntrepreneur(@RequestBody UserDao userDao) {
        try {
            return ResponseEntity.status(201).body(userService.registerEntrepreneur(userDao));
        }
        catch (Exception e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @PostMapping(value = "/login", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> loginUser(
            @RequestParam String username,
            @RequestParam String password
    ) {
        try {
            return ResponseEntity.status(200).body(userService.verifyLogin(username, password));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping(value = "/validate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validateJwtToken(HttpServletRequest request){
        try {
            log.info("Validating JWT token");
            return ResponseEntity.status(200).body(userService.validateJwtToken(request.getHeader(HttpHeaders.AUTHORIZATION).substring(7)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @GetMapping("/debug")
    public ResponseEntity<String> debugRoles(Authentication authentication) {
        return ResponseEntity.ok(authentication.getAuthorities().toString());
    }

}