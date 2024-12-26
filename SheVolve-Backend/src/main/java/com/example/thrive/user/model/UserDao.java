package com.example.thrive.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDao {

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String password;

    private UserRole userRole;

    @Override
    public String toString() {
        return "UserDao{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + userRole +
                '}';
    }

    public static UserModel toUserModel(UserDao userDao) {
        return UserModel.builder()
                .firstName(userDao.getFirstName())
                .lastName(userDao.getLastName())
                .username(userDao.getUsername())
                .email(userDao.getEmail())
                .userRole(userDao.getUserRole())
                .build();
    }

    public static UserDao fromUserModel(UserModel userModel) {
        return UserDao.builder()
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .username(userModel.getUsername())
                .email(userModel.getEmail())
                .userRole(userModel.getUserRole())
                .build();
    }
}
