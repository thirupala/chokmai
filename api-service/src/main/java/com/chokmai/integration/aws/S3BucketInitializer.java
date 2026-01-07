package com.chokmai.integration.aws;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.services.s3.S3Client;

public class S3BucketInitializer {

    @Inject
    S3Client s3Client;

    @ConfigProperty(name = "uploads.bucket",defaultValue = "uploads")
    String bucket;

    void onStart(@Observes StartupEvent ev) {
        try {
            s3Client.createBucket(b -> b.bucket(bucket));
        } catch (Exception ignored) {
            // bucket already exists
        }
    }
}
