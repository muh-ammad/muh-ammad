package com.themalomars.gpecmanager.web.rest;

import com.themalomars.gpecmanager.domain.OperationExterieur;
import com.themalomars.gpecmanager.service.OperationExterieurService;
import com.themalomars.gpecmanager.web.rest.errors.BadRequestAlertException;
import com.themalomars.gpecmanager.service.dto.OperationExterieurCriteria;
import com.themalomars.gpecmanager.service.OperationExterieurQueryService;

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
 * REST controller for managing {@link com.themalomars.gpecmanager.domain.OperationExterieur}.
 */
@RestController
@RequestMapping("/api")
public class OperationExterieurResource {

    private final Logger log = LoggerFactory.getLogger(OperationExterieurResource.class);

    private static final String ENTITY_NAME = "operationExterieur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OperationExterieurService operationExterieurService;

    private final OperationExterieurQueryService operationExterieurQueryService;

    public OperationExterieurResource(OperationExterieurService operationExterieurService, OperationExterieurQueryService operationExterieurQueryService) {
        this.operationExterieurService = operationExterieurService;
        this.operationExterieurQueryService = operationExterieurQueryService;
    }

    /**
     * {@code POST  /operation-exterieurs} : Create a new operationExterieur.
     *
     * @param operationExterieur the operationExterieur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new operationExterieur, or with status {@code 400 (Bad Request)} if the operationExterieur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/operation-exterieurs")
    public ResponseEntity<OperationExterieur> createOperationExterieur(@Valid @RequestBody OperationExterieur operationExterieur) throws URISyntaxException {
        log.debug("REST request to save OperationExterieur : {}", operationExterieur);
        if (operationExterieur.getId() != null) {
            throw new BadRequestAlertException("A new operationExterieur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OperationExterieur result = operationExterieurService.save(operationExterieur);
        return ResponseEntity.created(new URI("/api/operation-exterieurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /operation-exterieurs} : Updates an existing operationExterieur.
     *
     * @param operationExterieur the operationExterieur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operationExterieur,
     * or with status {@code 400 (Bad Request)} if the operationExterieur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the operationExterieur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/operation-exterieurs")
    public ResponseEntity<OperationExterieur> updateOperationExterieur(@Valid @RequestBody OperationExterieur operationExterieur) throws URISyntaxException {
        log.debug("REST request to update OperationExterieur : {}", operationExterieur);
        if (operationExterieur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OperationExterieur result = operationExterieurService.save(operationExterieur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, operationExterieur.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /operation-exterieurs} : get all the operationExterieurs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of operationExterieurs in body.
     */
    @GetMapping("/operation-exterieurs")
    public ResponseEntity<List<OperationExterieur>> getAllOperationExterieurs(OperationExterieurCriteria criteria, Pageable pageable) {
        log.debug("REST request to get OperationExterieurs by criteria: {}", criteria);
        Page<OperationExterieur> page = operationExterieurQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /operation-exterieurs/count} : count all the operationExterieurs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/operation-exterieurs/count")
    public ResponseEntity<Long> countOperationExterieurs(OperationExterieurCriteria criteria) {
        log.debug("REST request to count OperationExterieurs by criteria: {}", criteria);
        return ResponseEntity.ok().body(operationExterieurQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /operation-exterieurs/:id} : get the "id" operationExterieur.
     *
     * @param id the id of the operationExterieur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the operationExterieur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/operation-exterieurs/{id}")
    public ResponseEntity<OperationExterieur> getOperationExterieur(@PathVariable Long id) {
        log.debug("REST request to get OperationExterieur : {}", id);
        Optional<OperationExterieur> operationExterieur = operationExterieurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(operationExterieur);
    }

    /**
     * {@code DELETE  /operation-exterieurs/:id} : delete the "id" operationExterieur.
     *
     * @param id the id of the operationExterieur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/operation-exterieurs/{id}")
    public ResponseEntity<Void> deleteOperationExterieur(@PathVariable Long id) {
        log.debug("REST request to delete OperationExterieur : {}", id);
        operationExterieurService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
