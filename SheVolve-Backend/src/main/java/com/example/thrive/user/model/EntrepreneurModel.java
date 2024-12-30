package com.example.thrive.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Entity
@PrimaryKeyJoinColumn(name = "entrepreneur_id")
@Table(name = "entrepreneur")
public class EntrepreneurModel extends UserModel {


    @ManyToOne
    @JoinColumn(name = "mentor_id", foreignKey = @ForeignKey(name = "FK_entrepreneur_mentor"))
    private MentorModel mentor;

    @Override
    public String toString() {
        return "EntrepreneurModel{" +
                "role=" + userRole +
                ", mentor=" + mentor +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
