package com.caramelpoint.aeh.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.caramelpoint.aeh.domain.Form;
import com.caramelpoint.aeh.service.FormService;
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
 * REST controller for managing Form.
 */
@RestController
@RequestMapping("/api")
public class FormResource {

    private final Logger log = LoggerFactory.getLogger(FormResource.class);

    private static final String ENTITY_NAME = "form";

    private final FormService formService;

    public FormResource(FormService formService) {
        this.formService = formService;
    }

    /**
     * POST  /forms : Create a new form.
     *
     * @param form the form to create
     * @return the ResponseEntity with status 201 (Created) and with body the new form, or with status 400 (Bad Request) if the form has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/forms")
    @Timed
    public ResponseEntity<Form> createForm(@RequestBody Form form) throws URISyntaxException {
        log.debug("REST request to save Form : {}", form);
        if (form.getId() != null) {
            throw new BadRequestAlertException("A new form cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Form result = formService.save(form);
        return ResponseEntity.created(new URI("/api/forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /forms : Updates an existing form.
     *
     * @param form the form to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated form,
     * or with status 400 (Bad Request) if the form is not valid,
     * or with status 500 (Internal Server Error) if the form couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/forms")
    @Timed
    public ResponseEntity<Form> updateForm(@RequestBody Form form) throws URISyntaxException {
        log.debug("REST request to update Form : {}", form);
        if (form.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Form result = formService.save(form);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, form.getId().toString()))
            .body(result);
    }

    /**
     * GET  /forms : get all the forms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of forms in body
     */
    @GetMapping("/forms")
    @Timed
    public ResponseEntity<List<Form>> getAllForms(Pageable pageable) {
        log.debug("REST request to get a page of Forms");
        Page<Form> page = formService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/forms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /forms/:id : get the "id" form.
     *
     * @param id the id of the form to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the form, or with status 404 (Not Found)
     */
    @GetMapping("/forms/{id}")
    @Timed
    public ResponseEntity<Form> getForm(@PathVariable Long id) {
        log.debug("REST request to get Form : {}", id);
        Optional<Form> form = formService.findOne(id);
        return ResponseUtil.wrapOrNotFound(form);
    }

    /**
     * DELETE  /forms/:id : delete the "id" form.
     *
     * @param id the id of the form to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/forms/{id}")
    @Timed
    public ResponseEntity<Void> deleteForm(@PathVariable Long id) {
        log.debug("REST request to delete Form : {}", id);
        formService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
