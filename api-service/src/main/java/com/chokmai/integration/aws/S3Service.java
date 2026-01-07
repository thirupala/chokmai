package com.chokmai.integration.aws;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class S3Service {

    public String generateUploadUrl(String objectKey) {
        // Replace with real S3 presigned URL logic
        return "https://s3.amazonaws.com/my-bucket/" + objectKey;
    }
}
