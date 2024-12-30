package com.example.thrive.user.service;

import com.example.thrive.config.security.user.UserPrincipal;
import com.example.thrive.user.model.EntrepreneurModel;
import com.example.thrive.user.model.UserRole;
import com.example.thrive.user.model.product.Product;
import com.example.thrive.user.model.request.JoinRequest;
import com.example.thrive.user.repo.UserRepository;
import com.example.thrive.user.model.NGOModel;
import com.example.thrive.user.model.UserModel;
import com.example.thrive.user.repo.ProductRepository;
import com.example.thrive.user.repo.JoinRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.thrive.user.model.request.RequestType.*;

@Service
public class EntrepreneurService {

    private final UserRepository userRepository;

    private final JoinRequestRepository joinRequestRepository;

    private final ProductRepository productRepository;


    @Autowired
    public EntrepreneurService(UserRepository userRepository, JoinRequestRepository joinRequestRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.joinRequestRepository = joinRequestRepository;
        this.productRepository = productRepository;
    }

    public String sendJoinRequest(String username) {
        Optional<UserModel> userModel = userRepository.findByUsername(username);
        if (userModel.isPresent()) {
            NGOModel ngoModel = (NGOModel) userModel.get();
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            JoinRequest request = JoinRequest.builder()
                    .requester((EntrepreneurModel) (((UserPrincipal) principal).getUserModel()))
                    .responder(ngoModel)
                    .requestType(REQUEST_BY_ENTREPRENEUR_TO_NGO)
                    .build();
            joinRequestRepository.save(request);
            return "Request Sent";
        }
        throw new RuntimeException("NGO Not Found");
    }

    public String addProduct(String productName, String description, int price, MultipartFile image) throws IOException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EntrepreneurModel currentUser = ((EntrepreneurModel) ((UserPrincipal) principal).getUserModel());
        Product product = Product.builder()
                .productName(productName)
                .productPrice(price)
                .productImage(image.getBytes())
                .productOwner(currentUser)
                .createdBy(currentUser)
                .lastModifiedBy(currentUser)
                .productDescription(description)
                .build();
        productRepository.save(product);
        return "Product Added";
    }


    public String deleteProduct(int productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            productRepository.delete(product.get());
            return "Product Deleted";
        }
        throw new RuntimeException("Product Not Found");
    }

    public List<Product> getAllProducts() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EntrepreneurModel currentUser = ((EntrepreneurModel) ((UserPrincipal) principal).getUserModel());
        return productRepository.findAllByProductOwner(currentUser);
    }

    public List<String> getAllNgoNames() {
        List<UserModel> userModels = userRepository.findAllByUserRole(UserRole.ROLE_NGO);
        List<String> ngoNames = new ArrayList<>();
        userModels.forEach(userModel -> {
            ngoNames.add(userModel.getUsername());
        });
        return ngoNames;
    }

    public Boolean checkMentorshipStatus() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EntrepreneurModel currentUser = ((EntrepreneurModel) ((UserPrincipal) principal).getUserModel());
        return currentUser.getMentor() != null;

    }
}
