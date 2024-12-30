package com.example.thrive.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int userId;

    @NotNull(message = "first name can not be null")
    @NotBlank(message = "first name can not be blank")
    @Size(min = 2, max = 20, message = "first name must be 2-20 characters long")
    @Column(length = 20)
    protected String firstName;

    @NotNull(message = "last name can not be null")
    @NotBlank(message = "last name can not be blank")
    @Size(min = 2, max = 20, message = "last name must be 2-20 characters long")
    @Column(length = 20)
    protected String lastName;

    @NotNull(message = "username can not be null")
    @NotBlank(message = "username can not be blank")
    @Size(min = 8, max = 25, message = "username must be 8-25 characters long")
    @Column(length = 25, unique = true)
    protected String username;

    @Email(message = "provided email is not valid")
    @Column(length = 40, unique = true)
    protected String email;

    @NotNull(message = "password can not be null")
    @NotBlank(message = "password can not be blank")
    @Size(min = 8, max = 40, message = "password must be 8-40 characters long")
    @Column(length = 100)
    protected String password;

    @NotNull(message = "USER_ROLE can not be null")
    @Enumerated(EnumType.STRING)
    protected UserRole userRole;

}
