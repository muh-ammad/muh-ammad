package com.themalomars.gpecmanager.service.impl;

import com.themalomars.gpecmanager.service.SecteurActiviteService;
import com.themalomars.gpecmanager.domain.SecteurActivite;
import com.themalomars.gpecmanager.repository.SecteurActiviteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SecteurActivite}.
 */
@Service
@Transactional
public class SecteurActiviteServiceImpl implements SecteurActiviteService {

    private final Logger log = LoggerFactory.getLogger(SecteurActiviteServiceImpl.class);

    private final SecteurActiviteRepository secteurActiviteRepository;

    public SecteurActiviteServiceImpl(SecteurActiviteRepository secteurActiviteRepository) {
        this.secteurActiviteRepository = secteurActiviteRepository;
    }

    @Override
    public SecteurActivite save(SecteurActivite secteurActivite) {
        log.debug("Request to save SecteurActivite : {}", secteurActivite);
        return secteurActiviteRepository.save(secteurActivite);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SecteurActivite> findAll(Pageable pageable) {
        log.debug("Request to get all SecteurActivites");
        return secteurActiviteRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SecteurActivite> findOne(Long id) {
        log.debug("Request to get SecteurActivite : {}", id);
        return secteurActiviteRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SecteurActivite : {}", id);
        secteurActiviteRepository.deleteById(id);
    }
}
