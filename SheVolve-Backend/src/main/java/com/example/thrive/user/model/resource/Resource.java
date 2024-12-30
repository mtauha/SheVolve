package com.example.thrive.user.model.resource;

import com.example.thrive.user.model.mentorship.MentorShip;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int resourceId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "mentorship_id")
    private MentorShip mentorShip;

    private String resourceName;

    private String resourceDescription;

    @Enumerated(EnumType.STRING)
    private ResourceType resourceType;

    private String resourcePath;

}