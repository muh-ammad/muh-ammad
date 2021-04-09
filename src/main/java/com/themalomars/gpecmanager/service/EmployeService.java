package com.themalomars.gpecmanager.service;

import com.themalomars.gpecmanager.domain.Employe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Employe}.
 */
public interface EmployeService {

    /**
     * Save a employe.
     *
     * @param employe the entity to save.
     * @return the persisted entity.
     */
    Employe save(Employe employe);

    /**
     * Get all the employes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Employe> findAll(Pageable pageable);

    /**
     * Get all the employes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Employe> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" employe.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Employe> findOne(Long id);

    /**
     * Delete the "id" employe.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    void createIntituleEmploye(Employe employe);
    void definingAge(Employe employe);
    void definingDateRetraite(Employe employe);
}
