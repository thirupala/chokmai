package com.chokmai.grpc.jobs;

import com.chokmai.common.dto.jobs.UploadUrlResponse;
import com.chokmai.domain.jobs.CreateJobRequest;
import com.chokmai.integration.extraction.ExtractionService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;

@GrpcService
@RolesAllowed({"extractor", "admin", "sysadmin"})
public class JobGrpcService extends JobServiceGrpc.JobServiceImplBase {

    @Inject
    ExtractionService jobService;

    @Override
    public void submitAsyncJob(
            AsyncJobRequest request,
            StreamObserver<JobResponse> observer) {

        try {
            // Map gRPC request → domain request
            CreateJobRequest createJobRequest = map(request);

            // Call domain service
            UploadUrlResponse result =
                    jobService.submitAsync(createJobRequest);

            // Map domain response → gRPC response
            JobResponse response = JobResponse.newBuilder()
                    .setJobId(result.jobId().toString())
                    .setUploadUrl(result.uploadUrl())
                    .build();

            observer.onNext(response);
            observer.onCompleted();

        } catch (IllegalArgumentException e) {
            // Bad input (e.g. invalid UUID payload)
            observer.onError(
                    Status.INVALID_ARGUMENT
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        } catch (Exception e) {
            // Unexpected failure
            observer.onError(
                    Status.INTERNAL
                            .withDescription("Failed to submit async job")
                            .withCause(e)
                            .asRuntimeException()
            );
        }
    }

    /**
     * Maps gRPC AsyncJobRequest to domain CreateJobRequest.
     */
    private CreateJobRequest map(AsyncJobRequest request) {
        return new CreateJobRequest(
                request.getJobType(),
                request.getPayload()
        );
    }
}
