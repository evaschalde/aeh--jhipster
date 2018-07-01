package com.caramelpoint.aeh.service;

import com.caramelpoint.aeh.domain.ExternalResponsable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ExternalResponsable.
 */
public interface ExternalResponsableService {

    /**
     * Save a externalResponsable.
     *
     * @param externalResponsable the entity to save
     * @return the persisted entity
     */
    ExternalResponsable save(ExternalResponsable externalResponsable);

    /**
     * Get all the externalResponsables.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ExternalResponsable> findAll(Pageable pageable);


    /**
     * Get the "id" externalResponsable.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ExternalResponsable> findOne(Long id);

    /**
     * Delete the "id" externalResponsable.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
