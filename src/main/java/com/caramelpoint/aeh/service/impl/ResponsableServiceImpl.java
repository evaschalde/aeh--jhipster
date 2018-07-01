package com.caramelpoint.aeh.service.impl;

import com.caramelpoint.aeh.service.ResponsableService;
import com.caramelpoint.aeh.domain.Responsable;
import com.caramelpoint.aeh.repository.ResponsableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Responsable.
 */
@Service
@Transactional
public class ResponsableServiceImpl implements ResponsableService {

    private final Logger log = LoggerFactory.getLogger(ResponsableServiceImpl.class);

    private final ResponsableRepository responsableRepository;

    public ResponsableServiceImpl(ResponsableRepository responsableRepository) {
        this.responsableRepository = responsableRepository;
    }

    /**
     * Save a responsable.
     *
     * @param responsable the entity to save
     * @return the persisted entity
     */
    @Override
    public Responsable save(Responsable responsable) {
        log.debug("Request to save Responsable : {}", responsable);        return responsableRepository.save(responsable);
    }

    /**
     * Get all the responsables.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Responsable> findAll(Pageable pageable) {
        log.debug("Request to get all Responsables");
        return responsableRepository.findAll(pageable);
    }


    /**
     * Get one responsable by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Responsable> findOne(Long id) {
        log.debug("Request to get Responsable : {}", id);
        return responsableRepository.findById(id);
    }

    /**
     * Delete the responsable by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Responsable : {}", id);
        responsableRepository.deleteById(id);
    }
}
