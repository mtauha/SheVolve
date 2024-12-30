package com.example.thrive.user.service;

import com.example.thrive.config.security.user.UserPrincipal;
import com.example.thrive.user.model.EntrepreneurModel;
import com.example.thrive.user.model.MentorModel;
import com.example.thrive.user.model.NGOModel;
import com.example.thrive.user.model.UserModel;
import com.example.thrive.user.model.mentorship.MentorShip;
import com.example.thrive.user.model.request.JoinRequest;
import com.example.thrive.user.model.request.RequestStatus;
import com.example.thrive.user.repo.MentorshipRepository;
import com.example.thrive.user.repo.UserRepository;
import com.example.thrive.user.repo.JoinRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.example.thrive.user.model.UserRole.ROLE_MENTOR;
import static com.example.thrive.user.model.request.RequestType.*;

@Service
public class NgoService {

    private final UserRepository userRepository;

    private final JoinRequestRepository joinRequestRepository;

    private final MentorshipRepository mentorshipRepository;


    @Autowired
    public NgoService(UserRepository userRepository, JoinRequestRepository joinRequestRepository, MentorshipRepository mentorshipRepository) {
        this.userRepository = userRepository;
        this.joinRequestRepository = joinRequestRepository;
        this.mentorshipRepository = mentorshipRepository;
    }

    public String acceptOrRejectMentorJoinRequest(int requestId, boolean approve) {
        JoinRequest joinRequest = checkIfRequestExists(requestId);
        checkIfRequestIsOfMentor(joinRequest);
        if (approve) {
            joinRequest.setRequestStatus(RequestStatus.APPROVED);
            MentorModel mentorModel = (MentorModel) joinRequest.getRequester();
            mentorModel.setNgo( (NGOModel) joinRequest.getResponder());
            userRepository.save(mentorModel);
        }
        else {
            joinRequest.setRequestStatus(RequestStatus.DISAPPROVED);
        }


        joinRequestRepository.save(joinRequest);

        return String.format("Joining request of '%s' approved %s", joinRequest.getRequester().getUsername(), joinRequest.getRequestStatus().toString().toLowerCase());
    }

    public String acceptOrRejectEntrepreneurJoinRequest(String mentorName, int requestId, boolean approve) {
        JoinRequest joinRequest = checkIfRequestExists(requestId);
        checkIfRequestIsOfEntrepreneur(joinRequest);
        if (approve) {
            joinRequest.setRequestStatus(RequestStatus.APPROVED);
            Optional<UserModel> userModel = userRepository.findByUsername(mentorName);
            if (userModel.isEmpty()) {
                throw new IllegalArgumentException("Mentor does not exist");
            }

            MentorModel mentorModel = (MentorModel) userModel.get();
            EntrepreneurModel entrepreneurModel = (EntrepreneurModel) joinRequest.getRequester();
            entrepreneurModel.setMentor(mentorModel);

            MentorShip mentorShip = MentorShip.builder()
                    .mentor(mentorModel)
                    .mentee(entrepreneurModel)
                    .endDate(new Date(System.currentTimeMillis() + 1000*60*60*24*20))
                    .build();

            mentorshipRepository.save(mentorShip);
            userRepository.save(entrepreneurModel);
        }
        else {
            joinRequest.setRequestStatus(RequestStatus.DISAPPROVED);
        }

        joinRequestRepository.save(joinRequest);
        
        return String.format("Joining request of '%s' approved %s", joinRequest.getRequester().getUsername(), joinRequest.getRequestStatus().toString().toLowerCase());
    }

    private JoinRequest checkIfRequestExists(int requestId) {
        Optional<JoinRequest> joinRequest = joinRequestRepository.findById(requestId);
        if (joinRequest.isEmpty()) {
            throw new IllegalArgumentException("Request with given Id does not exists");
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        NGOModel currentUser = ((NGOModel) ((UserPrincipal) principal).getUserModel());
        if (currentUser.getUserId() != joinRequest.get().getResponder().getUserId())
            throw new IllegalArgumentException("NGO can only accept it own requests");
        return joinRequest.get();
    }

    private void checkIfRequestIsOfMentor(JoinRequest joinRequest) {
        if (joinRequest.getRequestType() != REQUEST_BY_MENTOR_TO_NGO)
            throw new IllegalArgumentException("Provided request id is not of a mentor");
    }

    private void checkIfRequestIsOfEntrepreneur(JoinRequest joinRequest) {
        if (joinRequest.getRequestType() != REQUEST_BY_ENTREPRENEUR_TO_NGO)
            throw new IllegalArgumentException("Provided request id is not of an entrepreneur");
    }

    public String sendNgoCreationRequestToAdmin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        NGOModel currentUser = ((NGOModel) ((UserPrincipal) principal).getUserModel());

        Optional<JoinRequest> optJoinRequest = joinRequestRepository.findById(currentUser.getUserId());
        if (optJoinRequest.isEmpty()) {
            UserModel admin = userRepository.findByUsername("admin").get();
            JoinRequest joinRequest = JoinRequest.builder()
                    .requestType(REQUEST_BY_NGO_TO_ADMIN)
                    .requester(currentUser)
                    .responder(admin)
                    .build();
            joinRequestRepository.save(joinRequest);
            return "request sent successfully";
        }
        else {
            JoinRequest joinRequest = optJoinRequest.get();
            joinRequest.setRequestDate(new Date());
            joinRequestRepository.save(joinRequest);
            return "Request reminder sent successfully";
        }
    }

    public List<MentorModel> getMentors() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        NGOModel currentUser = ((NGOModel) ((UserPrincipal) principal).getUserModel());

        List<UserModel> users = userRepository.findAllByUserRole(ROLE_MENTOR);
        List<MentorModel> mentorModels = new ArrayList<>();
        users.forEach(userModel -> {
            if ( ((MentorModel)userModel).getNgo() != null && ((MentorModel)userModel).getNgo().getUserId() == currentUser.getUserId())
                mentorModels.add((MentorModel) userModel);
        });
        return mentorModels;
    }
}
