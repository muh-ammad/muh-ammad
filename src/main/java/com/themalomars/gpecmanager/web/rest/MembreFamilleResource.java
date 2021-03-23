package com.themalomars.gpecmanager.web.rest;

import com.themalomars.gpecmanager.domain.MembreFamille;
import com.themalomars.gpecmanager.service.MembreFamilleService;
import com.themalomars.gpecmanager.web.rest.errors.BadRequestAlertException;
import com.themalomars.gpecmanager.service.dto.MembreFamilleCriteria;
import com.themalomars.gpecmanager.service.MembreFamilleQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.themalomars.gpecmanager.domain.MembreFamille}.
 */
@RestController
@RequestMapping("/api")
public class MembreFamilleResource {

    private final Logger log = LoggerFactory.getLogger(MembreFamilleResource.class);

    private static final String ENTITY_NAME = "membreFamille";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MembreFamilleService membreFamilleService;

    private final MembreFamilleQueryService membreFamilleQueryService;

    public MembreFamilleResource(MembreFamilleService membreFamilleService, MembreFamilleQueryService membreFamilleQueryService) {
        this.membreFamilleService = membreFamilleService;
        this.membreFamilleQueryService = membreFamilleQueryService;
    }

    /**
     * {@code POST  /membre-familles} : Create a new membreFamille.
     *
     * @param membreFamille the membreFamille to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new membreFamille, or with status {@code 400 (Bad Request)} if the membreFamille has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/membre-familles")
    public ResponseEntity<MembreFamille> createMembreFamille(@Valid @RequestBody MembreFamille membreFamille) throws URISyntaxException {
        log.debug("REST request to save MembreFamille : {}", membreFamille);
        if (membreFamille.getId() != null) {
            throw new BadRequestAlertException("A new membreFamille cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MembreFamille result = membreFamilleService.save(membreFamille);
        return ResponseEntity.created(new URI("/api/membre-familles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /membre-familles} : Updates an existing membreFamille.
     *
     * @param membreFamille the membreFamille to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated membreFamille,
     * or with status {@code 400 (Bad Request)} if the membreFamille is not valid,
     * or with status {@code 500 (Internal Server Error)} if the membreFamille couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/membre-familles")
    public ResponseEntity<MembreFamille> updateMembreFamille(@Valid @RequestBody MembreFamille membreFamille) throws URISyntaxException {
        log.debug("REST request to update MembreFamille : {}", membreFamille);
        if (membreFamille.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MembreFamille result = membreFamilleService.save(membreFamille);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, membreFamille.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /membre-familles} : get all the membreFamilles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of membreFamilles in body.
     */
    @GetMapping("/membre-familles")
    public ResponseEntity<List<MembreFamille>> getAllMembreFamilles(MembreFamilleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MembreFamilles by criteria: {}", criteria);
        Page<MembreFamille> page = membreFamilleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /membre-familles/count} : count all the membreFamilles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/membre-familles/count")
    public ResponseEntity<Long> countMembreFamilles(MembreFamilleCriteria criteria) {
        log.debug("REST request to count MembreFamilles by criteria: {}", criteria);
        return ResponseEntity.ok().body(membreFamilleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /membre-familles/:id} : get the "id" membreFamille.
     *
     * @param id the id of the membreFamille to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the membreFamille, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/membre-familles/{id}")
    public ResponseEntity<MembreFamille> getMembreFamille(@PathVariable Long id) {
        log.debug("REST request to get MembreFamille : {}", id);
        Optional<MembreFamille> membreFamille = membreFamilleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(membreFamille);
    }

    /**
     * {@code DELETE  /membre-familles/:id} : delete the "id" membreFamille.
     *
     * @param id the id of the membreFamille to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/membre-familles/{id}")
    public ResponseEntity<Void> deleteMembreFamille(@PathVariable Long id) {
        log.debug("REST request to delete MembreFamille : {}", id);
        membreFamilleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
