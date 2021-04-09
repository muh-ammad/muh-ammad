package com.themalomars.gpecmanager.web.rest;

import com.themalomars.gpecmanager.domain.Contrat;
import com.themalomars.gpecmanager.domain.Employe;
import com.themalomars.gpecmanager.service.ContratService;
import com.themalomars.gpecmanager.service.EmployeService;
import com.themalomars.gpecmanager.web.rest.errors.BadRequestAlertException;
import com.themalomars.gpecmanager.service.dto.ContratCriteria;
import com.themalomars.gpecmanager.service.ContratQueryService;

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
 * REST controller for managing
 * {@link com.themalomars.gpecmanager.domain.Contrat}.
 */
@RestController
@RequestMapping("/api")
public class ContratResource {

    private final Logger log = LoggerFactory.getLogger(ContratResource.class);

    private static final String ENTITY_NAME = "contrat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContratService contratService;

    private final ContratQueryService contratQueryService;

    public ContratResource(ContratService contratService, ContratQueryService contratQueryService) {
        this.contratService = contratService;
        this.contratQueryService = contratQueryService;
    }

    /**
     * {@code POST  /contrats} : Create a new contrat.
     *
     * @param contrat the contrat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new contrat, or with status {@code 400 (Bad Request)} if the
     *         contrat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contrats")
    public ResponseEntity<Contrat> createContrat(@Valid @RequestBody Contrat contrat) throws URISyntaxException {
        log.debug("REST request to save Contrat : {}", contrat);
        if (contrat.getId() != null) {
            throw new BadRequestAlertException("A new contrat cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Contrat result = contratService.save(contrat);
        // MAJ id_contrat dans employe
        contratService.updateEmploye(result);
        return ResponseEntity
                .created(new URI("/api/contrats/" + result.getId())).headers(HeaderUtil
                        .createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /contrats} : Updates an existing contrat.
     *
     * @param contrat the contrat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated contrat, or with status {@code 400 (Bad Request)} if the
     *         contrat is not valid, or with status
     *         {@code 500 (Internal Server Error)} if the contrat couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contrats")
    public ResponseEntity<Contrat> updateContrat(@Valid @RequestBody Contrat contrat) throws URISyntaxException {
        log.debug("REST request to update Contrat : {}", contrat);
        if (contrat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Contrat result = contratService.save(contrat);
        return ResponseEntity.ok().headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contrat.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /contrats} : get all the contrats.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of contrats in body.
     */
    @GetMapping("/contrats")
    public ResponseEntity<List<Contrat>> getAllContrats(ContratCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Contrats by criteria: {}", criteria);
        Page<Contrat> page = contratQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contrats/count} : count all the contrats.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/contrats/count")
    public ResponseEntity<Long> countContrats(ContratCriteria criteria) {
        log.debug("REST request to count Contrats by criteria: {}", criteria);
        return ResponseEntity.ok().body(contratQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /contrats/:id} : get the "id" contrat.
     *
     * @param id the id of the contrat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the contrat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contrats/{id}")
    public ResponseEntity<Contrat> getContrat(@PathVariable Long id) {
        log.debug("REST request to get Contrat : {}", id);
        Optional<Contrat> contrat = contratService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contrat);
    }

    /**
     * {@code DELETE  /contrats/:id} : delete the "id" contrat.
     *
     * @param id the id of the contrat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contrats/{id}")
    public ResponseEntity<Void> deleteContrat(@PathVariable Long id) {
        log.debug("REST request to delete Contrat : {}", id);
        contratService.delete(id);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }
}
