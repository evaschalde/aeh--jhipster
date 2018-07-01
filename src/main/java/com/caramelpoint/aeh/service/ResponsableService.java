package com.caramelpoint.aeh.service;

import com.caramelpoint.aeh.domain.Responsable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Responsable.
 */
public interface ResponsableService {

    /**
     * Save a responsable.
     *
     * @param responsable the entity to save
     * @return the persisted entity
     */
    Responsable save(Responsable responsable);

    /**
     * Get all the responsables.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Responsable> findAll(Pageable pageable);


    /**
     * Get the "id" responsable.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Responsable> findOne(Long id);

    /**
     * Delete the "id" responsable.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
