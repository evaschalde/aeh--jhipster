package com.caramelpoint.aeh.service.impl;

import com.caramelpoint.aeh.service.FormService;
import com.caramelpoint.aeh.domain.Form;
import com.caramelpoint.aeh.repository.FormRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Form.
 */
@Service
@Transactional
public class FormServiceImpl implements FormService {

    private final Logger log = LoggerFactory.getLogger(FormServiceImpl.class);

    private final FormRepository formRepository;

    public FormServiceImpl(FormRepository formRepository) {
        this.formRepository = formRepository;
    }

    /**
     * Save a form.
     *
     * @param form the entity to save
     * @return the persisted entity
     */
    @Override
    public Form save(Form form) {
        log.debug("Request to save Form : {}", form);        return formRepository.save(form);
    }

    /**
     * Get all the forms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Form> findAll(Pageable pageable) {
        log.debug("Request to get all Forms");
        return formRepository.findAll(pageable);
    }


    /**
     * Get one form by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Form> findOne(Long id) {
        log.debug("Request to get Form : {}", id);
        return formRepository.findById(id);
    }

    /**
     * Delete the form by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Form : {}", id);
        formRepository.deleteById(id);
    }
}
