package com.themalomars.gpecmanager.service.impl;

import com.themalomars.gpecmanager.service.ServiceAffecteService;
import com.themalomars.gpecmanager.domain.ServiceAffecte;
import com.themalomars.gpecmanager.repository.ServiceAffecteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceAffecte}.
 */
@Service
@Transactional
public class ServiceAffecteServiceImpl implements ServiceAffecteService {

    private final Logger log = LoggerFactory.getLogger(ServiceAffecteServiceImpl.class);

    private final ServiceAffecteRepository serviceAffecteRepository;

    public ServiceAffecteServiceImpl(ServiceAffecteRepository serviceAffecteRepository) {
        this.serviceAffecteRepository = serviceAffecteRepository;
    }

    @Override
    public ServiceAffecte save(ServiceAffecte serviceAffecte) {
        log.debug("Request to save ServiceAffecte : {}", serviceAffecte);
        return serviceAffecteRepository.save(serviceAffecte);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServiceAffecte> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceAffectes");
        return serviceAffecteRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceAffecte> findOne(Long id) {
        log.debug("Request to get ServiceAffecte : {}", id);
        return serviceAffecteRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceAffecte : {}", id);
        serviceAffecteRepository.deleteById(id);
    }
}
