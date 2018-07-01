package com.caramelpoint.aeh.service.impl;

import com.caramelpoint.aeh.service.BranchOfficeService;
import com.caramelpoint.aeh.domain.BranchOffice;
import com.caramelpoint.aeh.repository.BranchOfficeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing BranchOffice.
 */
@Service
@Transactional
public class BranchOfficeServiceImpl implements BranchOfficeService {

    private final Logger log = LoggerFactory.getLogger(BranchOfficeServiceImpl.class);

    private final BranchOfficeRepository branchOfficeRepository;

    public BranchOfficeServiceImpl(BranchOfficeRepository branchOfficeRepository) {
        this.branchOfficeRepository = branchOfficeRepository;
    }

    /**
     * Save a branchOffice.
     *
     * @param branchOffice the entity to save
     * @return the persisted entity
     */
    @Override
    public BranchOffice save(BranchOffice branchOffice) {
        log.debug("Request to save BranchOffice : {}", branchOffice);        return branchOfficeRepository.save(branchOffice);
    }

    /**
     * Get all the branchOffices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BranchOffice> findAll(Pageable pageable) {
        log.debug("Request to get all BranchOffices");
        return branchOfficeRepository.findAll(pageable);
    }


    /**
     * Get one branchOffice by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BranchOffice> findOne(Long id) {
        log.debug("Request to get BranchOffice : {}", id);
        return branchOfficeRepository.findById(id);
    }

    /**
     * Delete the branchOffice by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BranchOffice : {}", id);
        branchOfficeRepository.deleteById(id);
    }
}
