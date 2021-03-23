package com.themalomars.gpecmanager.web.rest;

import com.themalomars.gpecmanager.domain.ServiceAffecte;
import com.themalomars.gpecmanager.service.ServiceAffecteService;
import com.themalomars.gpecmanager.web.rest.errors.BadRequestAlertException;
import com.themalomars.gpecmanager.service.dto.ServiceAffecteCriteria;
import com.themalomars.gpecmanager.service.ServiceAffecteQueryService;

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
 * REST controller for managing {@link com.themalomars.gpecmanager.domain.ServiceAffecte}.
 */
@RestController
@RequestMapping("/api")
public class ServiceAffecteResource {

    private final Logger log = LoggerFactory.getLogger(ServiceAffecteResource.class);

    private static final String ENTITY_NAME = "serviceAffecte";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceAffecteService serviceAffecteService;

    private final ServiceAffecteQueryService serviceAffecteQueryService;

    public ServiceAffecteResource(ServiceAffecteService serviceAffecteService, ServiceAffecteQueryService serviceAffecteQueryService) {
        this.serviceAffecteService = serviceAffecteService;
        this.serviceAffecteQueryService = serviceAffecteQueryService;
    }

    /**
     * {@code POST  /service-affectes} : Create a new serviceAffecte.
     *
     * @param serviceAffecte the serviceAffecte to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceAffecte, or with status {@code 400 (Bad Request)} if the serviceAffecte has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-affectes")
    public ResponseEntity<ServiceAffecte> createServiceAffecte(@Valid @RequestBody ServiceAffecte serviceAffecte) throws URISyntaxException {
        log.debug("REST request to save ServiceAffecte : {}", serviceAffecte);
        if (serviceAffecte.getId() != null) {
            throw new BadRequestAlertException("A new serviceAffecte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceAffecte result = serviceAffecteService.save(serviceAffecte);
        return ResponseEntity.created(new URI("/api/service-affectes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-affectes} : Updates an existing serviceAffecte.
     *
     * @param serviceAffecte the serviceAffecte to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceAffecte,
     * or with status {@code 400 (Bad Request)} if the serviceAffecte is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceAffecte couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-affectes")
    public ResponseEntity<ServiceAffecte> updateServiceAffecte(@Valid @RequestBody ServiceAffecte serviceAffecte) throws URISyntaxException {
        log.debug("REST request to update ServiceAffecte : {}", serviceAffecte);
        if (serviceAffecte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceAffecte result = serviceAffecteService.save(serviceAffecte);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceAffecte.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-affectes} : get all the serviceAffectes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceAffectes in body.
     */
    @GetMapping("/service-affectes")
    public ResponseEntity<List<ServiceAffecte>> getAllServiceAffectes(ServiceAffecteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ServiceAffectes by criteria: {}", criteria);
        Page<ServiceAffecte> page = serviceAffecteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-affectes/count} : count all the serviceAffectes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/service-affectes/count")
    public ResponseEntity<Long> countServiceAffectes(ServiceAffecteCriteria criteria) {
        log.debug("REST request to count ServiceAffectes by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceAffecteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-affectes/:id} : get the "id" serviceAffecte.
     *
     * @param id the id of the serviceAffecte to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceAffecte, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-affectes/{id}")
    public ResponseEntity<ServiceAffecte> getServiceAffecte(@PathVariable Long id) {
        log.debug("REST request to get ServiceAffecte : {}", id);
        Optional<ServiceAffecte> serviceAffecte = serviceAffecteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceAffecte);
    }

    /**
     * {@code DELETE  /service-affectes/:id} : delete the "id" serviceAffecte.
     *
     * @param id the id of the serviceAffecte to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-affectes/{id}")
    public ResponseEntity<Void> deleteServiceAffecte(@PathVariable Long id) {
        log.debug("REST request to delete ServiceAffecte : {}", id);
        serviceAffecteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
