package com.themalomars.gpecmanager.service;

import com.themalomars.gpecmanager.domain.ServiceFrequente;
import com.themalomars.gpecmanager.repository.ServiceFrequenteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceFrequente}.
 */
@Service
@Transactional
public class ServiceFrequenteService {

    private final Logger log = LoggerFactory.getLogger(ServiceFrequenteService.class);

    private final ServiceFrequenteRepository serviceFrequenteRepository;

    public ServiceFrequenteService(ServiceFrequenteRepository serviceFrequenteRepository) {
        this.serviceFrequenteRepository = serviceFrequenteRepository;
    }

    /**
     * Save a serviceFrequente.
     *
     * @param serviceFrequente the entity to save.
     * @return the persisted entity.
     */
    public ServiceFrequente save(ServiceFrequente serviceFrequente) {
        log.debug("Request to save ServiceFrequente : {}", serviceFrequente);
        return serviceFrequenteRepository.save(serviceFrequente);
    }

    /**
     * Get all the serviceFrequentes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceFrequente> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceFrequentes");
        return serviceFrequenteRepository.findAll(pageable);
    }


    /**
     * Get one serviceFrequente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceFrequente> findOne(Long id) {
        log.debug("Request to get ServiceFrequente : {}", id);
        return serviceFrequenteRepository.findById(id);
    }

    /**
     * Delete the serviceFrequente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceFrequente : {}", id);
        serviceFrequenteRepository.deleteById(id);
    }
}
