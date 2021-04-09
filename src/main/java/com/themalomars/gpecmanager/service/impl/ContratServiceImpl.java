package com.themalomars.gpecmanager.service.impl;

import com.themalomars.gpecmanager.service.ContratService;
import com.themalomars.gpecmanager.domain.Contrat;
import com.themalomars.gpecmanager.domain.Employe;
import com.themalomars.gpecmanager.repository.ContratRepository;
import com.themalomars.gpecmanager.repository.EmployeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Contrat}.
 */
@Service
@Transactional
public class ContratServiceImpl implements ContratService {

    private final Logger log = LoggerFactory.getLogger(ContratServiceImpl.class);

    private final ContratRepository contratRepository;
    private final EmployeRepository employeRepository;

    public ContratServiceImpl(ContratRepository contratRepository, EmployeRepository employeRepository) {
        this.contratRepository = contratRepository;
        this.employeRepository = employeRepository;
    }

    @Override
    public Contrat save(Contrat contrat) {
        log.debug("Request to save Contrat : {}", contrat);
        return contratRepository.save(contrat);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Contrat> findAll(Pageable pageable) {
        log.debug("Request to get all Contrats");
        return contratRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Contrat> findOne(Long id) {
        log.debug("Request to get Contrat : {}", id);
        return contratRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Contrat : {}", id);
        contratRepository.deleteById(id);
    }

    @Override
    public void updateEmploye(Contrat contrat) {
        log.debug(" updateEmploye Contrat: {}", contrat.getEmploye());
        Employe employe = contrat.getEmploye();
        employe.setContrat(contrat);
        employeRepository.save(employe);
    }

}
