package com.example.thrive.user.service.cloud;

import com.example.thrive.user.model.resource.ResourceType;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class CloudService {

    @Value("${gcp.bucket.name}")
    private String BUCKET_NAME;

    private final Storage storage;

    public CloudService(Storage storage) {
        this.storage = storage;
    }

    public ResourceType getFileType(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null) {
            return ResourceType.OTHER;
        }

        if (contentType.startsWith("image/")) {
            return ResourceType.IMAGE;
        } else if (contentType.startsWith("video/")) {
            return ResourceType.VIDEO;
        } else if (contentType.startsWith("application/pdf")) {
            return ResourceType.DOCUMENT;
        } else if (contentType.startsWith("application/")) {
            return ResourceType.OTHER;
        } else {
            return ResourceType.OTHER;
        }
    }

    private boolean checkIfResourceAlreadyExists(String resourceName) {
        try {
            Blob blob = storage.get(BUCKET_NAME, resourceName);
            return blob != null && blob.exists();
        } catch (Exception e) {
            return false;
        }
    }

    public String uploadResource(String resourceName, MultipartFile resourceFile, ResourceType resourceType) throws IOException {
        if (resourceType == ResourceType.OTHER)
            throw new IllegalArgumentException("Resource can only be video, image or pdf");

        String resourcePath = resourceType + File.separator + resourceName;
        if (checkIfResourceAlreadyExists(resourceName))
            throw new IllegalArgumentException("Resource with same name already exists");

        BlobInfo blobInfo = BlobInfo.newBuilder(BUCKET_NAME, resourcePath).build();
        storage.create(blobInfo, resourceFile.getBytes());

        return resourcePath;
    }

}
