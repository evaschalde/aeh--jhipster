package com.caramelpoint.aeh.service.impl;

import com.caramelpoint.aeh.service.ExternalResponsableService;
import com.caramelpoint.aeh.domain.ExternalResponsable;
import com.caramelpoint.aeh.repository.ExternalResponsableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing ExternalResponsable.
 */
@Service
@Transactional
public class ExternalResponsableServiceImpl implements ExternalResponsableService {

    private final Logger log = LoggerFactory.getLogger(ExternalResponsableServiceImpl.class);

    private final ExternalResponsableRepository externalResponsableRepository;

    public ExternalResponsableServiceImpl(ExternalResponsableRepository externalResponsableRepository) {
        this.externalResponsableRepository = externalResponsableRepository;
    }

    /**
     * Save a externalResponsable.
     *
     * @param externalResponsable the entity to save
     * @return the persisted entity
     */
    @Override
    public ExternalResponsable save(ExternalResponsable externalResponsable) {
        log.debug("Request to save ExternalResponsable : {}", externalResponsable);        return externalResponsableRepository.save(externalResponsable);
    }

    /**
     * Get all the externalResponsables.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ExternalResponsable> findAll(Pageable pageable) {
        log.debug("Request to get all ExternalResponsables");
        return externalResponsableRepository.findAll(pageable);
    }


    /**
     * Get one externalResponsable by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ExternalResponsable> findOne(Long id) {
        log.debug("Request to get ExternalResponsable : {}", id);
        return externalResponsableRepository.findById(id);
    }

    /**
     * Delete the externalResponsable by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExternalResponsable : {}", id);
        externalResponsableRepository.deleteById(id);
    }
}
