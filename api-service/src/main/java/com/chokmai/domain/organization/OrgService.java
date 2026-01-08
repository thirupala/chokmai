package com.chokmai.domain.organization;


import com.chokmai.persistence.entities.organization.OrganizationEntity;
import com.chokmai.persistence.repositories.organization.OrgRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.BadRequestException;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class OrgService {

    @Inject
    OrgRepository orgRepository;

    /**
     * Create a new organization
     */
    @Transactional
    public OrganizationDTO create(@Valid CreateOrganizationRequest request) {
        // Check if organization with same name already exists
        if (orgRepository.existsByName(request.name())) {
            throw new BadRequestException("Organization with name '" + request.name() + "' already exists");
        }

        OrganizationEntity entity = new OrganizationEntity();
        entity.setName(request.name());

        orgRepository.persist(entity);

        return toDTO(entity);
    }

    /**
     * Get organization by ID
     */
    public OrganizationDTO getById(@NotNull UUID id) {
        OrganizationEntity entity = orgRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Organization not found with id: " + id));
        return toDTO(entity);
    }

    /**
     * Get all organizations with pagination
     */
    public PaginatedResponse<OrganizationDTO> getAll(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new BadRequestException("Invalid pagination parameters");
        }

        List<OrganizationDTO> organizations = orgRepository.findPaginated(page, size)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        long total = orgRepository.countAll();
        int totalPages = (int) Math.ceil((double) total / size);

        return new PaginatedResponse<>(organizations, page, size, total, totalPages);
    }

    /**
     * Update organization
     */
    @Transactional
    public OrganizationDTO update(@NotNull UUID id, @Valid UpdateOrganizationRequest request) {
        OrganizationEntity entity = orgRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Organization not found with id: " + id));

        // Check if another organization with the new name exists
        if (!entity.getName().equalsIgnoreCase(request.name()) &&
                orgRepository.existsByName(request.name())) {
            throw new BadRequestException("Organization with name '" + request.name() + "' already exists");
        }

        entity.setName(request.name());
        // updatedAt is handled by @PreUpdate in entity

        return toDTO(entity);
    }

    /**
     * Delete organization
     */
    @Transactional
    public void delete(@NotNull UUID id) {
        OrganizationEntity entity = orgRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Organization not found with id: " + id));

        orgRepository.delete(entity);
    }

    /**
     * Search organizations by name
     */
    public List<OrganizationDTO> searchByName(@NotBlank String namePattern) {
        return orgRepository.searchByName(namePattern)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get recent organizations
     */
    public List<OrganizationDTO> getRecent(int limit) {
        if (limit <= 0 || limit > 100) {
            throw new BadRequestException("Limit must be between 1 and 100");
        }

        return orgRepository.findRecentOrganizations(limit)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Check if organization exists
     */
    public boolean exists(@NotNull UUID id) {
        return orgRepository.findByIdOptional(id).isPresent();
    }

    /**
     * Convert entity to DTO
     */
    private OrganizationDTO toDTO(OrganizationEntity entity) {
        return new OrganizationDTO(
                entity.getId(),
                entity.getName(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    // ===== DTOs =====

    public record OrganizationDTO(
            UUID id,
            String name,
            Instant createdAt,
            Instant updatedAt
    ) {}

    public record CreateOrganizationRequest(
            @NotBlank(message = "Organization name is required")
            String name
    ) {}

    public record UpdateOrganizationRequest(
            @NotBlank(message = "Organization name is required")
            String name
    ) {}

    public record PaginatedResponse<T>(
            List<T> data,
            int page,
            int size,
            long total,
            int totalPages
    ) {}
}
