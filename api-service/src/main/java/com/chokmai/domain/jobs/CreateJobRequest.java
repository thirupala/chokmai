package com.chokmai.domain.jobs;

import java.util.UUID;

/**
 * Request to create an async job (S3-based upload)
 */
public record CreateJobRequest(
        UUID projectId,
        UUID processorId,
        String fileName,
        String contentType
)
{
        /**
         * Transport-level convenience constructor.
         * Converts gRPC string payload into domain identifiers.
         *
         * Expected payload format:
         *   "<projectId>:<processorId>"
         */
        public CreateJobRequest(String jobType, String payload) {
            this(
                    parseProjectId(payload),
                    parseProcessorId(payload),
                    parseFileName(payload),
                    parseContentType(payload)
            );
        }
    private static UUID parseProjectId(String payload) {
            try {
                return UUID.fromString(payload.split(":")[0]);
            } catch (Exception e) {
                throw new IllegalArgumentException(
                        "Invalid payload format for projectId: " + payload, e
                );
            }
        }

        private static UUID parseProcessorId(String payload) {
            try {
                return UUID.fromString(payload.split(":")[1]);
            } catch (Exception e) {
                throw new IllegalArgumentException(
                        "Invalid payload format for processorId: " + payload, e
                );
            }
        }
    private static String parseFileName(String payload) {
        try {
            return payload.split(":")[2];
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid payload format for processorId: " + payload, e
            );
        }
    }
    private static String parseContentType(String payload) {
        try {
            return payload.split(":")[3];
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid payload format for processorId: " + payload, e
            );
        }
    }
}
