package com.caramelpoint.aeh.service;

import com.caramelpoint.aeh.domain.Form;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Form.
 */
public interface FormService {

    /**
     * Save a form.
     *
     * @param form the entity to save
     * @return the persisted entity
     */
    Form save(Form form);

    /**
     * Get all the forms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Form> findAll(Pageable pageable);


    /**
     * Get the "id" form.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Form> findOne(Long id);

    /**
     * Delete the "id" form.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
