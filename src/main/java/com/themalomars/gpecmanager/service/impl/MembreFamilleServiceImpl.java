package com.themalomars.gpecmanager.service.impl;

import com.themalomars.gpecmanager.service.MembreFamilleService;
import com.themalomars.gpecmanager.domain.MembreFamille;
import com.themalomars.gpecmanager.repository.MembreFamilleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MembreFamille}.
 */
@Service
@Transactional
public class MembreFamilleServiceImpl implements MembreFamilleService {

    private final Logger log = LoggerFactory.getLogger(MembreFamilleServiceImpl.class);

    private final MembreFamilleRepository membreFamilleRepository;

    public MembreFamilleServiceImpl(MembreFamilleRepository membreFamilleRepository) {
        this.membreFamilleRepository = membreFamilleRepository;
    }

    @Override
    public MembreFamille save(MembreFamille membreFamille) {
        log.debug("Request to save MembreFamille : {}", membreFamille);
        return membreFamilleRepository.save(membreFamille);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MembreFamille> findAll(Pageable pageable) {
        log.debug("Request to get all MembreFamilles");
        return membreFamilleRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MembreFamille> findOne(Long id) {
        log.debug("Request to get MembreFamille : {}", id);
        return membreFamilleRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MembreFamille : {}", id);
        membreFamilleRepository.deleteById(id);
    }
}
