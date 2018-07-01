package com.caramelpoint.aeh.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caramelpoint.aeh.domain.ExternalResponsable;
import com.caramelpoint.aeh.service.ExternalResponsableService;
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
 * REST controller for managing ExternalResponsable.
 */
@RestController
@RequestMapping("/api")
public class ExternalResponsableResource {

    private final Logger log = LoggerFactory.getLogger(ExternalResponsableResource.class);

    private static final String ENTITY_NAME = "externalResponsable";

    private final ExternalResponsableService externalResponsableService;

    public ExternalResponsableResource(ExternalResponsableService externalResponsableService) {
        this.externalResponsableService = externalResponsableService;
    }

    /**
     * POST  /external-responsables : Create a new externalResponsable.
     *
     * @param externalResponsable the externalResponsable to create
     * @return the ResponseEntity with status 201 (Created) and with body the new externalResponsable, or with status 400 (Bad Request) if the externalResponsable has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/external-responsables")
    @Timed
    public ResponseEntity<ExternalResponsable> createExternalResponsable(@RequestBody ExternalResponsable externalResponsable) throws URISyntaxException {
        log.debug("REST request to save ExternalResponsable : {}", externalResponsable);
        if (externalResponsable.getId() != null) {
            throw new BadRequestAlertException("A new externalResponsable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExternalResponsable result = externalResponsableService.save(externalResponsable);
        return ResponseEntity.created(new URI("/api/external-responsables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /external-responsables : Updates an existing externalResponsable.
     *
     * @param externalResponsable the externalResponsable to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated externalResponsable,
     * or with status 400 (Bad Request) if the externalResponsable is not valid,
     * or with status 500 (Internal Server Error) if the externalResponsable couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/external-responsables")
    @Timed
    public ResponseEntity<ExternalResponsable> updateExternalResponsable(@RequestBody ExternalResponsable externalResponsable) throws URISyntaxException {
        log.debug("REST request to update ExternalResponsable : {}", externalResponsable);
        if (externalResponsable.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExternalResponsable result = externalResponsableService.save(externalResponsable);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, externalResponsable.getId().toString()))
            .body(result);
    }

    /**
     * GET  /external-responsables : get all the externalResponsables.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of externalResponsables in body
     */
    @GetMapping("/external-responsables")
    @Timed
    public ResponseEntity<List<ExternalResponsable>> getAllExternalResponsables(Pageable pageable) {
        log.debug("REST request to get a page of ExternalResponsables");
        Page<ExternalResponsable> page = externalResponsableService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/external-responsables");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /external-responsables/:id : get the "id" externalResponsable.
     *
     * @param id the id of the externalResponsable to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the externalResponsable, or with status 404 (Not Found)
     */
    @GetMapping("/external-responsables/{id}")
    @Timed
    public ResponseEntity<ExternalResponsable> getExternalResponsable(@PathVariable Long id) {
        log.debug("REST request to get ExternalResponsable : {}", id);
        Optional<ExternalResponsable> externalResponsable = externalResponsableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(externalResponsable);
    }

    /**
     * DELETE  /external-responsables/:id : delete the "id" externalResponsable.
     *
     * @param id the id of the externalResponsable to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/external-responsables/{id}")
    @Timed
    public ResponseEntity<Void> deleteExternalResponsable(@PathVariable Long id) {
        log.debug("REST request to delete ExternalResponsable : {}", id);
        externalResponsableService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
