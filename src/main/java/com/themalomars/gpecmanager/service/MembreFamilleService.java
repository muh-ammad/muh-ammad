package com.themalomars.gpecmanager.service;

import com.themalomars.gpecmanager.domain.MembreFamille;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link MembreFamille}.
 */
public interface MembreFamilleService {

    /**
     * Save a membreFamille.
     *
     * @param membreFamille the entity to save.
     * @return the persisted entity.
     */
    MembreFamille save(MembreFamille membreFamille);

    /**
     * Get all the membreFamilles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MembreFamille> findAll(Pageable pageable);


    /**
     * Get the "id" membreFamille.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MembreFamille> findOne(Long id);

    /**
     * Delete the "id" membreFamille.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
