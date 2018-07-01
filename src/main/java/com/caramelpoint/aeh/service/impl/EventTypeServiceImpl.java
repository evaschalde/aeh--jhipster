package com.caramelpoint.aeh.service.impl;

import com.caramelpoint.aeh.service.EventTypeService;
import com.caramelpoint.aeh.domain.EventType;
import com.caramelpoint.aeh.repository.EventTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing EventType.
 */
@Service
@Transactional
public class EventTypeServiceImpl implements EventTypeService {

    private final Logger log = LoggerFactory.getLogger(EventTypeServiceImpl.class);

    private final EventTypeRepository eventTypeRepository;

    public EventTypeServiceImpl(EventTypeRepository eventTypeRepository) {
        this.eventTypeRepository = eventTypeRepository;
    }

    /**
     * Save a eventType.
     *
     * @param eventType the entity to save
     * @return the persisted entity
     */
    @Override
    public EventType save(EventType eventType) {
        log.debug("Request to save EventType : {}", eventType);        return eventTypeRepository.save(eventType);
    }

    /**
     * Get all the eventTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EventType> findAll(Pageable pageable) {
        log.debug("Request to get all EventTypes");
        return eventTypeRepository.findAll(pageable);
    }


    /**
     * Get one eventType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EventType> findOne(Long id) {
        log.debug("Request to get EventType : {}", id);
        return eventTypeRepository.findById(id);
    }

    /**
     * Delete the eventType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventType : {}", id);
        eventTypeRepository.deleteById(id);
    }
}
