package com.themalomars.gpecmanager.service;

import com.themalomars.gpecmanager.domain.OperationExterieur;
import com.themalomars.gpecmanager.repository.OperationExterieurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OperationExterieur}.
 */
@Service
@Transactional
public class OperationExterieurService {

    private final Logger log = LoggerFactory.getLogger(OperationExterieurService.class);

    private final OperationExterieurRepository operationExterieurRepository;

    public OperationExterieurService(OperationExterieurRepository operationExterieurRepository) {
        this.operationExterieurRepository = operationExterieurRepository;
    }

    /**
     * Save a operationExterieur.
     *
     * @param operationExterieur the entity to save.
     * @return the persisted entity.
     */
    public OperationExterieur save(OperationExterieur operationExterieur) {
        log.debug("Request to save OperationExterieur : {}", operationExterieur);
        return operationExterieurRepository.save(operationExterieur);
    }

    /**
     * Get all the operationExterieurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OperationExterieur> findAll(Pageable pageable) {
        log.debug("Request to get all OperationExterieurs");
        return operationExterieurRepository.findAll(pageable);
    }


    /**
     * Get one operationExterieur by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OperationExterieur> findOne(Long id) {
        log.debug("Request to get OperationExterieur : {}", id);
        return operationExterieurRepository.findById(id);
    }

    /**
     * Delete the operationExterieur by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OperationExterieur : {}", id);
        operationExterieurRepository.deleteById(id);
    }
}
