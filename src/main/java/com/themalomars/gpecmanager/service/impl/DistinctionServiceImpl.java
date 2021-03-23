package com.themalomars.gpecmanager.service.impl;

import com.themalomars.gpecmanager.service.DistinctionService;
import com.themalomars.gpecmanager.domain.Distinction;
import com.themalomars.gpecmanager.repository.DistinctionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Distinction}.
 */
@Service
@Transactional
public class DistinctionServiceImpl implements DistinctionService {

    private final Logger log = LoggerFactory.getLogger(DistinctionServiceImpl.class);

    private final DistinctionRepository distinctionRepository;

    public DistinctionServiceImpl(DistinctionRepository distinctionRepository) {
        this.distinctionRepository = distinctionRepository;
    }

    @Override
    public Distinction save(Distinction distinction) {
        log.debug("Request to save Distinction : {}", distinction);
        return distinctionRepository.save(distinction);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Distinction> findAll(Pageable pageable) {
        log.debug("Request to get all Distinctions");
        return distinctionRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Distinction> findOne(Long id) {
        log.debug("Request to get Distinction : {}", id);
        return distinctionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Distinction : {}", id);
        distinctionRepository.deleteById(id);
    }
}
