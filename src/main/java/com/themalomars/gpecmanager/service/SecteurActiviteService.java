package com.themalomars.gpecmanager.service;

import com.themalomars.gpecmanager.domain.SecteurActivite;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link SecteurActivite}.
 */
public interface SecteurActiviteService {

    /**
     * Save a secteurActivite.
     *
     * @param secteurActivite the entity to save.
     * @return the persisted entity.
     */
    SecteurActivite save(SecteurActivite secteurActivite);

    /**
     * Get all the secteurActivites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SecteurActivite> findAll(Pageable pageable);


    /**
     * Get the "id" secteurActivite.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SecteurActivite> findOne(Long id);

    /**
     * Delete the "id" secteurActivite.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
