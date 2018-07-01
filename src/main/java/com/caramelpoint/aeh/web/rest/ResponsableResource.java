package com.caramelpoint.aeh.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caramelpoint.aeh.domain.Responsable;
import com.caramelpoint.aeh.service.ResponsableService;
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
 * REST controller for managing Responsable.
 */
@RestController
@RequestMapping("/api")
public class ResponsableResource {

    private final Logger log = LoggerFactory.getLogger(ResponsableResource.class);

    private static final String ENTITY_NAME = "responsable";

    private final ResponsableService responsableService;

    public ResponsableResource(ResponsableService responsableService) {
        this.responsableService = responsableService;
    }

    /**
     * POST  /responsables : Create a new responsable.
     *
     * @param responsable the responsable to create
     * @return the ResponseEntity with status 201 (Created) and with body the new responsable, or with status 400 (Bad Request) if the responsable has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/responsables")
    @Timed
    public ResponseEntity<Responsable> createResponsable(@RequestBody Responsable responsable) throws URISyntaxException {
        log.debug("REST request to save Responsable : {}", responsable);
        if (responsable.getId() != null) {
            throw new BadRequestAlertException("A new responsable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Responsable result = responsableService.save(responsable);
        return ResponseEntity.created(new URI("/api/responsables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /responsables : Updates an existing responsable.
     *
     * @param responsable the responsable to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated responsable,
     * or with status 400 (Bad Request) if the responsable is not valid,
     * or with status 500 (Internal Server Error) if the responsable couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/responsables")
    @Timed
    public ResponseEntity<Responsable> updateResponsable(@RequestBody Responsable responsable) throws URISyntaxException {
        log.debug("REST request to update Responsable : {}", responsable);
        if (responsable.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Responsable result = responsableService.save(responsable);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, responsable.getId().toString()))
            .body(result);
    }

    /**
     * GET  /responsables : get all the responsables.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of responsables in body
     */
    @GetMapping("/responsables")
    @Timed
    public ResponseEntity<List<Responsable>> getAllResponsables(Pageable pageable) {
        log.debug("REST request to get a page of Responsables");
        Page<Responsable> page = responsableService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/responsables");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /responsables/:id : get the "id" responsable.
     *
     * @param id the id of the responsable to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the responsable, or with status 404 (Not Found)
     */
    @GetMapping("/responsables/{id}")
    @Timed
    public ResponseEntity<Responsable> getResponsable(@PathVariable Long id) {
        log.debug("REST request to get Responsable : {}", id);
        Optional<Responsable> responsable = responsableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(responsable);
    }

    /**
     * DELETE  /responsables/:id : delete the "id" responsable.
     *
     * @param id the id of the responsable to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/responsables/{id}")
    @Timed
    public ResponseEntity<Void> deleteResponsable(@PathVariable Long id) {
        log.debug("REST request to delete Responsable : {}", id);
        responsableService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
