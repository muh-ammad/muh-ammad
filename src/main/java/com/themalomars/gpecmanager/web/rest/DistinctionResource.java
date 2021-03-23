package com.themalomars.gpecmanager.web.rest;

import com.themalomars.gpecmanager.domain.Distinction;
import com.themalomars.gpecmanager.service.DistinctionService;
import com.themalomars.gpecmanager.web.rest.errors.BadRequestAlertException;
import com.themalomars.gpecmanager.service.dto.DistinctionCriteria;
import com.themalomars.gpecmanager.service.DistinctionQueryService;

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
 * REST controller for managing {@link com.themalomars.gpecmanager.domain.Distinction}.
 */
@RestController
@RequestMapping("/api")
public class DistinctionResource {

    private final Logger log = LoggerFactory.getLogger(DistinctionResource.class);

    private static final String ENTITY_NAME = "distinction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DistinctionService distinctionService;

    private final DistinctionQueryService distinctionQueryService;

    public DistinctionResource(DistinctionService distinctionService, DistinctionQueryService distinctionQueryService) {
        this.distinctionService = distinctionService;
        this.distinctionQueryService = distinctionQueryService;
    }

    /**
     * {@code POST  /distinctions} : Create a new distinction.
     *
     * @param distinction the distinction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new distinction, or with status {@code 400 (Bad Request)} if the distinction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/distinctions")
    public ResponseEntity<Distinction> createDistinction(@Valid @RequestBody Distinction distinction) throws URISyntaxException {
        log.debug("REST request to save Distinction : {}", distinction);
        if (distinction.getId() != null) {
            throw new BadRequestAlertException("A new distinction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Distinction result = distinctionService.save(distinction);
        return ResponseEntity.created(new URI("/api/distinctions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /distinctions} : Updates an existing distinction.
     *
     * @param distinction the distinction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated distinction,
     * or with status {@code 400 (Bad Request)} if the distinction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the distinction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/distinctions")
    public ResponseEntity<Distinction> updateDistinction(@Valid @RequestBody Distinction distinction) throws URISyntaxException {
        log.debug("REST request to update Distinction : {}", distinction);
        if (distinction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Distinction result = distinctionService.save(distinction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, distinction.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /distinctions} : get all the distinctions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of distinctions in body.
     */
    @GetMapping("/distinctions")
    public ResponseEntity<List<Distinction>> getAllDistinctions(DistinctionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Distinctions by criteria: {}", criteria);
        Page<Distinction> page = distinctionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /distinctions/count} : count all the distinctions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/distinctions/count")
    public ResponseEntity<Long> countDistinctions(DistinctionCriteria criteria) {
        log.debug("REST request to count Distinctions by criteria: {}", criteria);
        return ResponseEntity.ok().body(distinctionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /distinctions/:id} : get the "id" distinction.
     *
     * @param id the id of the distinction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the distinction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/distinctions/{id}")
    public ResponseEntity<Distinction> getDistinction(@PathVariable Long id) {
        log.debug("REST request to get Distinction : {}", id);
        Optional<Distinction> distinction = distinctionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(distinction);
    }

    /**
     * {@code DELETE  /distinctions/:id} : delete the "id" distinction.
     *
     * @param id the id of the distinction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/distinctions/{id}")
    public ResponseEntity<Void> deleteDistinction(@PathVariable Long id) {
        log.debug("REST request to delete Distinction : {}", id);
        distinctionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
