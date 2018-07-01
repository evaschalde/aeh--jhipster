package com.caramelpoint.aeh.service;

import com.caramelpoint.aeh.domain.BranchOffice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing BranchOffice.
 */
public interface BranchOfficeService {

    /**
     * Save a branchOffice.
     *
     * @param branchOffice the entity to save
     * @return the persisted entity
     */
    BranchOffice save(BranchOffice branchOffice);

    /**
     * Get all the branchOffices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BranchOffice> findAll(Pageable pageable);


    /**
     * Get the "id" branchOffice.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BranchOffice> findOne(Long id);

    /**
     * Delete the "id" branchOffice.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
