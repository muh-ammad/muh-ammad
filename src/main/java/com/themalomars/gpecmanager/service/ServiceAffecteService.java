package com.themalomars.gpecmanager.service;

import com.themalomars.gpecmanager.domain.ServiceAffecte;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ServiceAffecte}.
 */
public interface ServiceAffecteService {

    /**
     * Save a serviceAffecte.
     *
     * @param serviceAffecte the entity to save.
     * @return the persisted entity.
     */
    ServiceAffecte save(ServiceAffecte serviceAffecte);

    /**
     * Get all the serviceAffectes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServiceAffecte> findAll(Pageable pageable);


    /**
     * Get the "id" serviceAffecte.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceAffecte> findOne(Long id);

    /**
     * Delete the "id" serviceAffecte.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
