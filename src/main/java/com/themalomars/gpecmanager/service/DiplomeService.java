package com.themalomars.gpecmanager.service;

import com.themalomars.gpecmanager.domain.Diplome;
import com.themalomars.gpecmanager.repository.DiplomeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Diplome}.
 */
@Service
@Transactional
public class DiplomeService {

    private final Logger log = LoggerFactory.getLogger(DiplomeService.class);

    private final DiplomeRepository diplomeRepository;

    public DiplomeService(DiplomeRepository diplomeRepository) {
        this.diplomeRepository = diplomeRepository;
    }

    /**
     * Save a diplome.
     *
     * @param diplome the entity to save.
     * @return the persisted entity.
     */
    public Diplome save(Diplome diplome) {
        log.debug("Request to save Diplome : {}", diplome);
        return diplomeRepository.save(diplome);
    }

    /**
     * Get all the diplomes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Diplome> findAll(Pageable pageable) {
        log.debug("Request to get all Diplomes");
        return diplomeRepository.findAll(pageable);
    }


    /**
     * Get one diplome by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Diplome> findOne(Long id) {
        log.debug("Request to get Diplome : {}", id);
        return diplomeRepository.findById(id);
    }

    /**
     * Delete the diplome by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Diplome : {}", id);
        diplomeRepository.deleteById(id);
    }
}
