package com.example.thrive.user.model.dao;

import com.example.thrive.user.model.request.JoinRequest;
import com.example.thrive.user.model.request.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AdminRequestDao {

    private String request;

    private RequestStatus requestStatus;

    private String requesterName;

    private Date requestTime;

    public static AdminRequestDao fromJoinRequest(JoinRequest joinRequest) {
        return AdminRequestDao.builder()
                .request(joinRequest.getRequest())
                .requestStatus(joinRequest.getRequestStatus())
                .requesterName(joinRequest.getRequester().getUsername())
                .requestTime(joinRequest.getRequestDate())
                .build();
    }
}
