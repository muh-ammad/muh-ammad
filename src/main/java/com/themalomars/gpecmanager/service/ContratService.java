package com.themalomars.gpecmanager.service;

import com.themalomars.gpecmanager.domain.Contrat;
import com.themalomars.gpecmanager.domain.Employe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Contrat}.
 */
public interface ContratService {

    /**
     * Save a contrat.
     *
     * @param contrat the entity to save.
     * @return the persisted entity.
     */
    Contrat save(Contrat contrat);

    /**
     * Get all the contrats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Contrat> findAll(Pageable pageable);


    /**
     * Get the "id" contrat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Contrat> findOne(Long id);

    /**
     * Delete the "id" contrat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    void updateEmploye(Contrat contrat);
}
