package com.caramelpoint.aeh.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caramelpoint.aeh.domain.BranchOffice;
import com.caramelpoint.aeh.service.BranchOfficeService;
import com.caramelpoint.aeh.web.rest.errors.BadRequestAlertException;
import com.caramelpoint.aeh.web.rest.util.HeaderUtil;
import com.caramelpoint.aeh.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BranchOffice.
 */
@RestController
@RequestMapping("/api")
public class BranchOfficeResource {

    private final Logger log = LoggerFactory.getLogger(BranchOfficeResource.class);

    private static final String ENTITY_NAME = "branchOffice";

    private final BranchOfficeService branchOfficeService;

    public BranchOfficeResource(BranchOfficeService branchOfficeService) {
        this.branchOfficeService = branchOfficeService;
    }

    /**
     * POST  /branch-offices : Create a new branchOffice.
     *
     * @param branchOffice the branchOffice to create
     * @return the ResponseEntity with status 201 (Created) and with body the new branchOffice, or with status 400 (Bad Request) if the branchOffice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/branch-offices")
    @Timed
    public ResponseEntity<BranchOffice> createBranchOffice(@RequestBody BranchOffice branchOffice) throws URISyntaxException {
        log.debug("REST request to save BranchOffice : {}", branchOffice);
        if (branchOffice.getId() != null) {
            throw new BadRequestAlertException("A new branchOffice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BranchOffice result = branchOfficeService.save(branchOffice);
        return ResponseEntity.created(new URI("/api/branch-offices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /branch-offices : Updates an existing branchOffice.
     *
     * @param branchOffice the branchOffice to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated branchOffice,
     * or with status 400 (Bad Request) if the branchOffice is not valid,
     * or with status 500 (Internal Server Error) if the branchOffice couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/branch-offices")
    @Timed
    public ResponseEntity<BranchOffice> updateBranchOffice(@RequestBody BranchOffice branchOffice) throws URISyntaxException {
        log.debug("REST request to update BranchOffice : {}", branchOffice);
        if (branchOffice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BranchOffice result = branchOfficeService.save(branchOffice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, branchOffice.getId().toString()))
            .body(result);
    }

    /**
     * GET  /branch-offices : get all the branchOffices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of branchOffices in body
     */
    @GetMapping("/branch-offices")
    @Timed
    public ResponseEntity<List<BranchOffice>> getAllBranchOffices(Pageable pageable) {
        log.debug("REST request to get a page of BranchOffices");
        Page<BranchOffice> page = branchOfficeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/branch-offices");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /branch-offices/:id : get the "id" branchOffice.
     *
     * @param id the id of the branchOffice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the branchOffice, or with status 404 (Not Found)
     */
    @GetMapping("/branch-offices/{id}")
    @Timed
    public ResponseEntity<BranchOffice> getBranchOffice(@PathVariable Long id) {
        log.debug("REST request to get BranchOffice : {}", id);
        Optional<BranchOffice> branchOffice = branchOfficeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(branchOffice);
    }

    /**
     * DELETE  /branch-offices/:id : delete the "id" branchOffice.
     *
     * @param id the id of the branchOffice to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/branch-offices/{id}")
    @Timed
    public ResponseEntity<Void> deleteBranchOffice(@PathVariable Long id) {
        log.debug("REST request to delete BranchOffice : {}", id);
        branchOfficeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
