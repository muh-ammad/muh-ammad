package com.themalomars.gpecmanager.web.rest;

import com.themalomars.gpecmanager.domain.ServiceFrequente;
import com.themalomars.gpecmanager.service.ServiceFrequenteService;
import com.themalomars.gpecmanager.web.rest.errors.BadRequestAlertException;
import com.themalomars.gpecmanager.service.dto.ServiceFrequenteCriteria;
import com.themalomars.gpecmanager.service.ServiceFrequenteQueryService;

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
 * REST controller for managing {@link com.themalomars.gpecmanager.domain.ServiceFrequente}.
 */
@RestController
@RequestMapping("/api")
public class ServiceFrequenteResource {

    private final Logger log = LoggerFactory.getLogger(ServiceFrequenteResource.class);

    private static final String ENTITY_NAME = "serviceFrequente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceFrequenteService serviceFrequenteService;

    private final ServiceFrequenteQueryService serviceFrequenteQueryService;

    public ServiceFrequenteResource(ServiceFrequenteService serviceFrequenteService, ServiceFrequenteQueryService serviceFrequenteQueryService) {
        this.serviceFrequenteService = serviceFrequenteService;
        this.serviceFrequenteQueryService = serviceFrequenteQueryService;
    }

    /**
     * {@code POST  /service-frequentes} : Create a new serviceFrequente.
     *
     * @param serviceFrequente the serviceFrequente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceFrequente, or with status {@code 400 (Bad Request)} if the serviceFrequente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-frequentes")
    public ResponseEntity<ServiceFrequente> createServiceFrequente(@Valid @RequestBody ServiceFrequente serviceFrequente) throws URISyntaxException {
        log.debug("REST request to save ServiceFrequente : {}", serviceFrequente);
        if (serviceFrequente.getId() != null) {
            throw new BadRequestAlertException("A new serviceFrequente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceFrequente result = serviceFrequenteService.save(serviceFrequente);
        return ResponseEntity.created(new URI("/api/service-frequentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-frequentes} : Updates an existing serviceFrequente.
     *
     * @param serviceFrequente the serviceFrequente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceFrequente,
     * or with status {@code 400 (Bad Request)} if the serviceFrequente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceFrequente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-frequentes")
    public ResponseEntity<ServiceFrequente> updateServiceFrequente(@Valid @RequestBody ServiceFrequente serviceFrequente) throws URISyntaxException {
        log.debug("REST request to update ServiceFrequente : {}", serviceFrequente);
        if (serviceFrequente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceFrequente result = serviceFrequenteService.save(serviceFrequente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceFrequente.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-frequentes} : get all the serviceFrequentes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceFrequentes in body.
     */
    @GetMapping("/service-frequentes")
    public ResponseEntity<List<ServiceFrequente>> getAllServiceFrequentes(ServiceFrequenteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ServiceFrequentes by criteria: {}", criteria);
        Page<ServiceFrequente> page = serviceFrequenteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-frequentes/count} : count all the serviceFrequentes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/service-frequentes/count")
    public ResponseEntity<Long> countServiceFrequentes(ServiceFrequenteCriteria criteria) {
        log.debug("REST request to count ServiceFrequentes by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceFrequenteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-frequentes/:id} : get the "id" serviceFrequente.
     *
     * @param id the id of the serviceFrequente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceFrequente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-frequentes/{id}")
    public ResponseEntity<ServiceFrequente> getServiceFrequente(@PathVariable Long id) {
        log.debug("REST request to get ServiceFrequente : {}", id);
        Optional<ServiceFrequente> serviceFrequente = serviceFrequenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceFrequente);
    }

    /**
     * {@code DELETE  /service-frequentes/:id} : delete the "id" serviceFrequente.
     *
     * @param id the id of the serviceFrequente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-frequentes/{id}")
    public ResponseEntity<Void> deleteServiceFrequente(@PathVariable Long id) {
        log.debug("REST request to delete ServiceFrequente : {}", id);
        serviceFrequenteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
