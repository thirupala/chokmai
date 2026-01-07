package com.chokmai.persistence.repositories.jobs;

import com.chokmai.common.dto.jobs.JobResponse;
import com.chokmai.persistence.entities.jobs.JobEntity;
import com.chokmai.persistence.entities.processors.ProcessorEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class JobRepository implements PanacheRepositoryBase<JobEntity,UUID> {
    /**
     * Find job by TensorLake job ID (parse_id)
     * Used when receiving webhooks from TensorLake
     */
    public JobEntity findByTensorlakeJobId(String tensorlakeJobId) {
        return find("tensorlakeJobId", tensorlakeJobId).firstResult();
    }

    /**
     * Find job by TensorLake file ID
     */
    public JobEntity findByTensorlakeFileId(String tensorlakeFileId) {
        return find("tensorlakeFileId", tensorlakeFileId).firstResult();
    }

    /**
     * Find all jobs for a specific processor
     */
    public List<JobEntity> findByProcessor(UUID processorId) {
        return find("processor.id", Sort.descending("createdAt"), processorId).list();
    }

    /**
     * Find all jobs for a specific processor with pagination
     */
    public List<JobEntity> findByProcessor(UUID processorId, int page, int size) {
        return find("processor.id", Sort.descending("createdAt"), processorId)
                .page(page, size)
                .list();
    }

    /**
     * Find all jobs for a specific project
     */
    public List<JobEntity> findByProject(UUID projectId) {
        return find("processor.project.id", Sort.descending("createdAt"), projectId).list();
    }

    /**
     * Find all jobs for a specific project with pagination
     */
    public List<JobEntity> findByProject(UUID projectId, int page, int size) {
        return find("processor.project.id", Sort.descending("createdAt"), projectId)
                .page(page, size)
                .list();
    }

    /**
     * Find jobs by status
     */
    public List<JobEntity> findByStatus(String status) {
        return find("status", Sort.descending("createdAt"), status).list();
    }

    /**
     * Find jobs by status with pagination
     */
    public List<JobEntity> findByStatus(String status, int page, int size) {
        return find("status", Sort.descending("createdAt"), status)
                .page(page, size)
                .list();
    }

    /**
     * Find all pending jobs (for polling or retry logic)
     */
    public List<JobEntity> findPendingJobs() {
        return find("status IN ('pending', 'processing')", Sort.ascending("createdAt")).list();
    }

    /**
     * Find pending jobs older than specified minutes
     * Useful for identifying stuck jobs
     */
    public List<JobEntity> findStalePendingJobs(int minutesOld) {
        LocalDateTime cutoffTime = LocalDateTime.now().minusMinutes(minutesOld);
        return find("status IN ('pending', 'processing') AND createdAt < ?1",
                Sort.ascending("createdAt"), cutoffTime).list();
    }

    /**
     * Find completed jobs for a processor
     */
    public List<JobEntity> findCompletedJobsByProcessor(UUID processorId) {
        return find("processor.id = ?1 AND status = 'completed'",
                Sort.descending("completedAt"), processorId).list();
    }

    /**
     * Find failed jobs for a processor
     */
    public List<JobEntity> findFailedJobsByProcessor(UUID processorId) {
        return find("processor.id = ?1 AND status = 'failed'",
                Sort.descending("createdAt"), processorId).list();
    }

    /**
     * Count jobs by status for a processor
     */
    public long countByProcessorAndStatus(UUID processorId, String status) {
        return count("processor.id = ?1 AND status = ?2", processorId, status);
    }

    /**
     * Count all jobs for a processor
     */
    public long countByProcessor(UUID processorId) {
        return count("processor.id", processorId);
    }

    /**
     * Count all jobs for a project
     */
    public long countByProject(UUID projectId) {
        return count("processor.project.id", projectId);
    }

    /**
     * Find jobs created within date range
     */
    public List<JobEntity> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return find("createdAt BETWEEN ?1 AND ?2",
                Sort.descending("createdAt"), startDate, endDate).list();
    }

    /**
     * Find jobs for a processor within date range
     */
    public List<JobEntity> findByProcessorAndDateRange(
            UUID processorId,
            LocalDateTime startDate,
            LocalDateTime endDate) {
        return find("processor.id = ?1 AND createdAt BETWEEN ?2 AND ?3",
                Sort.descending("createdAt"), processorId, startDate, endDate).list();
    }

    /**
     * Find recent jobs for a processor (last N jobs)
     */
    public List<JobEntity> findRecentJobsByProcessor(UUID processorId, int limit) {
        return find("processor.id", Sort.descending("createdAt"), processorId)
                .page(0, limit)
                .list();
    }

    /**
     * Find jobs by multiple statuses
     */
    public List<JobEntity> findByStatuses(List<String> statuses) {
        return find("status IN ?1", Sort.descending("createdAt"), statuses).list();
    }

    /**
     * Find jobs by processor and multiple statuses
     */
    public List<JobEntity> findByProcessorAndStatuses(UUID processorId, List<String> statuses) {
        return find("processor.id = ?1 AND status IN ?2",
                Sort.descending("createdAt"), processorId, statuses).list();
    }

    /**
     * Delete jobs older than specified days (for cleanup)
     */
    public long deleteOldJobs(int daysOld) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(daysOld);
        return delete("createdAt < ?1", cutoffDate);
    }

    /**
     * Delete completed jobs older than specified days
     */
    public long deleteOldCompletedJobs(int daysOld) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(daysOld);
        return delete("status = 'completed' AND completedAt < ?1", cutoffDate);
    }
    @Transactional
    public void updateEstimatedCredits(UUID jobId, int estimatedCredits, String creditReservationId) {
        JobEntity job = findById(jobId);
        if (job == null) {
            throw new IllegalStateException("Job not found: " + jobId);
        }

        job.estimatedCredits = estimatedCredits;
        job.externalJobId = creditReservationId; // or add a dedicated column
        job.updatedAt = Instant.now();
    }

    /**
     * Check if job exists by TensorLake job ID
     * Useful for webhook idempotency
     */
    public boolean existsByTensorlakeJobId(String tensorlakeJobId) {
        return count("tensorlakeJobId", tensorlakeJobId) > 0;
    }

    /**
     * Find job with processor and project details (optimized query)
     */
    public Optional<JobEntity> findByIdWithDetails(UUID jobId) {
        return find("SELECT j FROM Job j " +
                "LEFT JOIN FETCH j.processor p " +
                "LEFT JOIN FETCH p.project " +
                "WHERE j.id = ?1", jobId).firstResultOptional();
    }

    /**
     * Get job statistics for a processor
     */
    public JobStatistics getJobStatistics(UUID processorId) {
        Long total = count("processor.id", processorId);
        Long completed = count("processor.id = ?1 AND status = 'completed'", processorId);
        Long failed = count("processor.id = ?1 AND status = 'failed'", processorId);
        Long pending = count("processor.id = ?1 AND status IN ('pending', 'processing')", processorId);

        return new JobStatistics(total, completed, failed, pending);
    }

    /**
     * Get job statistics for a project
     */
    public JobStatistics getProjectJobStatistics(UUID projectId) {
        Long total = count("processor.project.id", projectId);
        Long completed = count("processor.project.id = ?1 AND status = 'completed'", projectId);
        Long failed = count("processor.project.id = ?1 AND status = 'failed'", projectId);
        Long pending = count("processor.project.id = ?1 AND status IN ('pending', 'processing')", projectId);

        return new JobStatistics(total, completed, failed, pending);
    }

    /**
     * Find jobs that need retry (failed jobs that can be retried)
     */
    public List<JobEntity> findJobsForRetry(int maxRetries) {
        return find("status = 'failed' AND retryCount < ?1",
                Sort.ascending("createdAt"), maxRetries).list();
    }

    /**
     * Update job status by TensorLake job ID
     * Returns number of rows updated
     */
    public int updateStatus(String tensorlakeJobId, String newStatus) {
        return update("status = ?1, updatedAt = ?2 WHERE tensorlakeJobId = ?3",
                newStatus, LocalDateTime.now(), tensorlakeJobId);
    }

    /**
     * Batch update status for multiple jobs
     */
    public int batchUpdateStatus(List<String> tensorlakeJobIds, String newStatus) {
        return update("status = ?1, updatedAt = ?2 WHERE tensorlakeJobId IN ?3",
                newStatus, LocalDateTime.now(), tensorlakeJobIds);
    }

    /**
     * Inner class for job statistics
     */
    public static class JobStatistics {
        private final Long total;
        private final Long completed;
        private final Long failed;
        private final Long pending;

        public JobStatistics(Long total, Long completed, Long failed, Long pending) {
            this.total = total != null ? total : 0L;
            this.completed = completed != null ? completed : 0L;
            this.failed = failed != null ? failed : 0L;
            this.pending = pending != null ? pending : 0L;
        }

        public Long getTotal() {
            return total;
        }

        public Long getCompleted() {
            return completed;
        }

        public Long getFailed() {
            return failed;
        }

        public Long getPending() {
            return pending;
        }

        public double getSuccessRate() {
            if (total == 0) return 0.0;
            return (completed.doubleValue() / total.doubleValue()) * 100.0;
        }

        public double getFailureRate() {
            if (total == 0) return 0.0;
            return (failed.doubleValue() / total.doubleValue()) * 100.0;
        }
    }
    public Optional<JobEntity> findByIdAndTenant(UUID jobId, UUID tenantId) {
        return find(
                "id = ?1 and tenantId = ?2",
                jobId,
                tenantId
        ).firstResultOptional();
    }

    public List<JobEntity> findByProject(UUID projectId, UUID tenantId) {
        return find(
                "projectId = ?1 and tenantId = ?2",
                projectId,
                tenantId
        ).list();
    }

    public JobResponse findDto(UUID jobId, UUID tenantId) {
        return find(
                "id = ?1 and tenantId = ?2",
                jobId,
                tenantId
        )
                .project(JobResponse.class)
                .firstResult();
    }


    /**
     * Transactional job creation
     */
    @Transactional
    public JobEntity createTransactional(
            UUID tenantId,
            UUID projectId,
            UUID processorId,
            String fileName,
            Long fileSize,
            int estimatedCredits
    ) {
        ProcessorEntity processor =
                getEntityManager().getReference(
                        ProcessorEntity.class,
                        processorId
                );

        Instant now = Instant.now();

        JobEntity job = new JobEntity();
        job.id = UUID.randomUUID();
        job.tenantId = tenantId;
        job.projectId = projectId;
        job.processor = processor;
        job.fileName = fileName;
        job.fileSize = fileSize;
        job.status = "pending";
        job.retryCount = 0;
        job.estimatedCredits = estimatedCredits;
        job.actualCredits = 0;
        job.createdAt = now;
        job.updatedAt = now;
        job.deleted = false;

        persist(job);
        return job;
    }
}
