package com.example.thrive.user.model.dao;

import com.example.thrive.user.model.EntrepreneurModel;
import com.example.thrive.user.model.MentorModel;
import com.example.thrive.user.model.resource.Resource;
import com.example.thrive.user.model.resource.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResourceDao {

    private String mentorName;

    private String menteeName;

    private String resourceName;

    private String resourceDescription;

    private ResourceType resourceType;

    private String resourcePath;

    public static ResourceDao fromResource(Resource resource) {
        MentorModel mentorModel = resource.getMentorShip().getMentor();
        EntrepreneurModel menteeModel = resource.getMentorShip().getMentee();
        return ResourceDao.builder()
                .mentorName(mentorModel.getFirstName() + " " + mentorModel.getLastName())
                .menteeName(menteeModel.getFirstName() + " " + menteeModel.getLastName())
                .resourceName(resource.getResourceName())
                .resourceDescription(resource.getResourceDescription())
                .resourceType(resource.getResourceType())
                .resourcePath(resource.getResourcePath())
                .build();
    }
}
