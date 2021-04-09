package com.themalomars.gpecmanager.service.impl;

import com.themalomars.gpecmanager.service.EmployeService;
import com.themalomars.gpecmanager.domain.Employe;
import com.themalomars.gpecmanager.repository.EmployeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Optional;

import javax.validation.constraints.Null;

/**
 * Service Implementation for managing {@link Employe}.
 */
@Service
@Transactional
public class EmployeServiceImpl implements EmployeService {

    private final Logger log = LoggerFactory.getLogger(EmployeServiceImpl.class);

    private final EmployeRepository employeRepository;

    private final static int AGE_RETRAITE = 60;

    public EmployeServiceImpl(EmployeRepository employeRepository) {
        this.employeRepository = employeRepository;
    }

    @Override
    public Employe save(Employe employe) {
        log.debug("Request to save Employe : {}", employe);
        return employeRepository.save(employe);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Employe> findAll(Pageable pageable) {
        log.debug("Request to get all Employes");
        return employeRepository.findAll(pageable);
    }

    public Page<Employe> findAllWithEagerRelationships(Pageable pageable) {
        return employeRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Employe> findOne(Long id) {
        log.debug("Request to get Employe : {}", id);
        return employeRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Employe : {}", id);
        employeRepository.deleteById(id);
    }

    @Override
    public void createIntituleEmploye(Employe employe) {
        if (employe.getIntituleEmploye() == null) {
            employe.setIntituleEmploye(employe.getNom() + " " + employe.getPrenoms());
            employeRepository.save(employe);    
        }

    }

    @Override
    public void definingAge(Employe employe) {
        log.debug("Age Employe : {}", employe.getAge());
        if (employe.getAge() == null || employe.getAge() == 0) {
            LocalDate localDate = LocalDate.now();
            int yearNow = localDate.getYear();
            LocalDate dateNaissance = LocalDate.ofInstant(employe.getDateNaissance(), ZoneId.systemDefault());
            int yearNaissance = dateNaissance.getYear();
            Long age = (long) (yearNow - yearNaissance);
            employe.setAge(age);
            employeRepository.save(employe);
        }
    }

    @Override
    public void definingDateRetraite(Employe employe) {
        Long ageRetraite = ((long) AGE_RETRAITE) -  employe.getAge();        
        LocalDate dateEmbauchementDate = LocalDate.ofInstant(employe.getDateEmbauchement(), ZoneId.systemDefault());
        LocalDate dateRetraiteDate = dateEmbauchementDate.plusYears(ageRetraite);
        Instant dateRetraite = dateRetraiteDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        employe.setDateRetraite(dateRetraite);
        employeRepository.save(employe);
        log.debug(" dateRetraitDate : {}", dateRetraite);
    }
}
