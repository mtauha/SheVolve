package com.example.thrive.user.model;

import com.example.thrive.user.model.request.RequestStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Entity
@PrimaryKeyJoinColumn(name = "ngo_id")
@Table(name = "ngo")
public class NGOModel extends UserModel{

    @NotNull(message = "description can not be null")
    @Size(min = 20, max = 255)
    private String description;

    @OneToMany(mappedBy = "ngo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<MentorModel> mentors;

    @NotNull(message = "Request status can not be null")
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    @PrePersist
    protected void onCreate() {
        requestStatus = RequestStatus.WAITING;
    }

    @Override
    public String toString() {
        return "NGOModel{" +
                "role=" + userRole +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
