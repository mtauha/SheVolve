package com.example.thrive.user.controller;

import com.example.thrive.user.model.EntrepreneurModel;
import com.example.thrive.user.model.product.Product;
import com.example.thrive.user.service.EntrepreneurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @PostMapping("/product")
    ResponseEntity<String> addProduct(
            @RequestParam String productName,
            @RequestParam int price,
            @RequestParam String description,
            @RequestPart MultipartFile image
            ) {
        try {
            return ResponseEntity.status(201).body(entrepreneurService.addProduct(productName, description, price, image));
        }
        catch (Exception e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @DeleteMapping("/product/{productId}")
    ResponseEntity<String> deleteProduct(
            @PathVariable int productId
    ) {
        try {
            return ResponseEntity.status(201).body(entrepreneurService.deleteProduct(productId));
        }
        catch (Exception e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @GetMapping("/products")
    ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.status(200).body(entrepreneurService.getAllProducts());
    }

    @GetMapping("/ngos")
    ResponseEntity<List<String>> getAllNgos() {
        return ResponseEntity.status(200).body(entrepreneurService.getAllNgoNames());
    }

    @GetMapping("/status/mentorship")
    ResponseEntity<Boolean> checkMentorshipStatus() {
        return ResponseEntity.status(200).body(entrepreneurService.checkMentorshipStatus());
    }

}
