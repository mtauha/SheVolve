package com.example.thrive.user.model.request;

import com.example.thrive.user.model.NGOModel;
import com.example.thrive.user.model.UserModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class JoinRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int requestId;

    @NotNull(message = "Request can not be null")
    @Size(min = 20, max = 255, message = "request must be 20-255 character long")
    private String request;

    private Date requestDate;

    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    @NotNull(message = "Requester can not be null")
    @ManyToOne
    UserModel requester;

    @NotNull(message = "Responder can not be null")
    @ManyToOne
    UserModel responder;

    @NotNull(message = "Request type can not be null")
    @Enumerated(EnumType.STRING)
    RequestType requestType;


    @PrePersist
    public void prePersist() {
        requestDate = new Date(System.currentTimeMillis());
        requestStatus = RequestStatus.WAITING;
    }

}