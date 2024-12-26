package com.example.thrive.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@SuperBuilder
@Entity
@PrimaryKeyJoinColumn(name = "admin_id")
@Table(name = "admin")
public class AdminModel extends UserModel{

    @Override
    public String toString() {
        return "AdminModel{" +
                ", role=" + userRole +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
