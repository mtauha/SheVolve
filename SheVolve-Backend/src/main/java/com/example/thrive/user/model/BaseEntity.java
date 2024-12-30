package com.example.thrive.user.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@MappedSuperclass
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {

    protected Date createdAt;

    @ManyToOne()
    @JoinColumn(name = "creator_id")
    protected UserModel createdBy;

    protected Date lastModifiedAt;

    @ManyToOne()
    @JoinColumn(name = "modifier_id")
    protected UserModel lastModifiedBy;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        lastModifiedAt = new Date();
    }

}
