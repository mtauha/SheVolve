package com.example.thrive.user.service;

import com.example.thrive.config.security.user.UserPrincipal;
import com.example.thrive.user.model.*;
import com.example.thrive.user.model.mentorship.MentorShip;
import com.example.thrive.user.model.request.JoinRequest;
import com.example.thrive.user.model.resource.Resource;
import com.example.thrive.user.model.resource.ResourceType;
import com.example.thrive.user.repo.ResourceRepository;
import com.example.thrive.user.repo.UserRepository;
import com.example.thrive.user.repo.MentorshipRepository;
import com.example.thrive.user.repo.JoinRequestRepository;
import com.example.thrive.user.service.cloud.CloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.thrive.user.model.request.RequestType.*;

@Service
public class MentorService {

    private final UserRepository userRepository;

    private final JoinRequestRepository joinRequestRepository;

    private final MentorshipRepository mentorshipRepository;

    private final CloudService cloudService;

    private final ResourceRepository resourceRepository;

    @Autowired
    public MentorService(UserRepository userRepository, JoinRequestRepository joinRequestRepository, MentorshipRepository mentorshipRepository, CloudService cloudService, ResourceRepository resourceRepository) {
        this.userRepository = userRepository;
        this.joinRequestRepository = joinRequestRepository;
        this.mentorshipRepository = mentorshipRepository;
        this.cloudService = cloudService;
        this.resourceRepository = resourceRepository;
    }

    public String sendJoinRequest(String username) {
        Optional<UserModel> userModel = userRepository.findByUsername(username);
        if (userModel.isPresent()) {
            try {
                NGOModel ngoModel = (NGOModel) userModel.get();
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                JoinRequest request = JoinRequest.builder()
                        .requester((MentorModel) (((UserPrincipal) principal).getUserModel()))
                        .responder(ngoModel)
                        .requestType(REQUEST_BY_MENTOR_TO_NGO)
                        .build();
                joinRequestRepository.save(request);
            }
            catch (ClassCastException e) {
                throw new RuntimeException(e.getMessage());
            }
            return "Request Sent";
        }
        throw new RuntimeException("NGO Not Found");
    }

    public String addResource(int mentorshipId, String resourceName, String resourceDescription, MultipartFile resourceFile) {
        MentorShip mentorShip = checkIfMentorshipExists(mentorshipId);
        try {
            ResourceType resourceType = cloudService.getFileType(resourceFile);
            String resourcePath = cloudService.uploadResource(resourceName, resourceFile, resourceType);
            Resource resource = Resource.builder()
                    .resourceName(resourceName)
                    .resourceDescription(resourceDescription)
                    .resourcePath(resourcePath)
                    .resourceType(resourceType)
                    .build();
            resourceRepository.save(resource);
            return "Resource Added";
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    private MentorShip checkIfMentorshipExists(int id) {
        Optional<MentorShip> mentorShip = mentorshipRepository.findById(id);
        if (mentorShip.isPresent()) {
            return mentorShip.get();
        }
        throw new RuntimeException("Mentor Not Found");
    }

    public List<String> getAllNgoNames() {
        List<UserModel> userModels = userRepository.findAllByUserRole(UserRole.ROLE_NGO);
        List<String> ngoNames = new ArrayList<>();
        userModels.forEach(userModel -> {
            ngoNames.add(userModel.getUsername());
        });
        return ngoNames;
    }

    public List<EntrepreneurModel> getAllEntrepreneursUnderAMentor() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MentorModel currentUser = ((MentorModel) ((UserPrincipal) principal).getUserModel());

        return currentUser.getEntrepreneurs();
    }
}
