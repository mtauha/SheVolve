package com.example.thrive.user.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@PrimaryKeyJoinColumn(name = "mentor_id")
@Table(name = "mentor")
public class MentorModel extends UserModel {

    @ManyToOne
    @JoinColumn(name = "ngo_id", foreignKey = @ForeignKey(name = "FK_mentor_ngo"))
    private NGOModel ngo;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<EntrepreneurModel> entrepreneurs;

    @Override
    public String toString() {
        return "MentorModel{" +
                "role=" + userRole +
                ", ngo=" + ngo +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
