package com.themalomars.gpecmanager.service;

import com.themalomars.gpecmanager.domain.Distinction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Distinction}.
 */
public interface DistinctionService {

    /**
     * Save a distinction.
     *
     * @param distinction the entity to save.
     * @return the persisted entity.
     */
    Distinction save(Distinction distinction);

    /**
     * Get all the distinctions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Distinction> findAll(Pageable pageable);


    /**
     * Get the "id" distinction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Distinction> findOne(Long id);

    /**
     * Delete the "id" distinction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
