package com.chokmai.integration.llms;

import com.chokmai.persistence.entities.jobs.JobEntity;
import com.chokmai.persistence.entities.llm.LlmModelEntity;
import com.chokmai.persistence.repositories.llms.LlmModelRepository;
import com.chokmai.persistence.repositories.llms.LlmUsageLogRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;
import com.chokmai.persistence.entities.llm.LlmUsageLogEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@ApplicationScoped
public class LlmUsageService {

    private static final Logger LOG = Logger.getLogger(LlmUsageService.class);

    @Inject
    LlmModelRepository llmModelRepository;

    @Inject
    LlmUsageLogRepository llmUsageLogRepository;

    @Transactional
    public void logUsage(JobEntity job) {
        if (job.llmModelId == null || job.llmInputTokens == null || job.llmOutputTokens == null) {
            LOG.warn("Cannot log usage: missing LLM data");
            return;
        }

        LlmModelEntity model = llmModelRepository.findById(job.llmModelId);
        if (model == null) {
            LOG.errorf("LLM model %s not found", job.llmModelId);
            return;
        }

        // Calculate cost
        BigDecimal inputCost = model.costPer1kInputTokens
                .multiply(BigDecimal.valueOf(job.llmInputTokens))
                .divide(BigDecimal.valueOf(1000), 6, RoundingMode.HALF_UP);

        BigDecimal outputCost = model.costPer1kOutputTokens
                .multiply(BigDecimal.valueOf(job.llmOutputTokens))
                .divide(BigDecimal.valueOf(1000), 6, RoundingMode.HALF_UP);

        BigDecimal totalCost = inputCost.add(outputCost);

        // Update job
        job.llmCost = totalCost;

        // Log usage
        LlmUsageLogEntity usageLog = new LlmUsageLogEntity();
        usageLog.setTenantId(job.tenantId);
        usageLog.setInputTokens(job.llmInputTokens);
        usageLog.setOutputTokens(job.llmOutputTokens);
        usageLog.setTotalCost(totalCost);
        usageLog.setStatus(job.status);
        llmUsageLogRepository.persist(usageLog);

        LOG.infof("Logged LLM usage: %s/%s, tokens=%d/%d, cost=$%s",
                model.provider.displayName, model.displayName,
                job.llmInputTokens, job.llmOutputTokens, totalCost);
    }

    public BigDecimal getTenantMonthlySpend(UUID tenantId) {
        Instant startOfMonth = Instant.now().truncatedTo(ChronoUnit.DAYS)
                .minus(Instant.now().atZone(java.time.ZoneId.systemDefault()).getDayOfMonth() - 1, ChronoUnit.DAYS);

        return llmUsageLogRepository.getTotalCostByTenant(tenantId, startOfMonth);
    }
}
