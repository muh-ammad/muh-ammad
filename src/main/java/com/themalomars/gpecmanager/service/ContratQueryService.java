package com.themalomars.gpecmanager.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.themalomars.gpecmanager.domain.Contrat;
import com.themalomars.gpecmanager.domain.*; // for static metamodels
import com.themalomars.gpecmanager.repository.ContratRepository;
import com.themalomars.gpecmanager.service.dto.ContratCriteria;

/**
 * Service for executing complex queries for {@link Contrat} entities in the database.
 * The main input is a {@link ContratCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Contrat} or a {@link Page} of {@link Contrat} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContratQueryService extends QueryService<Contrat> {

    private final Logger log = LoggerFactory.getLogger(ContratQueryService.class);

    private final ContratRepository contratRepository;

    public ContratQueryService(ContratRepository contratRepository) {
        this.contratRepository = contratRepository;
    }

    /**
     * Return a {@link List} of {@link Contrat} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Contrat> findByCriteria(ContratCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Contrat> specification = createSpecification(criteria);
        return contratRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Contrat} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Contrat> findByCriteria(ContratCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Contrat> specification = createSpecification(criteria);
        return contratRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContratCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Contrat> specification = createSpecification(criteria);
        return contratRepository.count(specification);
    }

    /**
     * Function to convert {@link ContratCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Contrat> createSpecification(ContratCriteria criteria) {
        Specification<Contrat> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Contrat_.id));
            }
            if (criteria.getNumeroContrat() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroContrat(), Contrat_.numeroContrat));
            }
            if (criteria.getLibelleContrat() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelleContrat(), Contrat_.libelleContrat));
            }
            if (criteria.getDateDebut() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateDebut(), Contrat_.dateDebut));
            }
            if (criteria.getDateFin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateFin(), Contrat_.dateFin));
            }
            if (criteria.getNiveauEtude() != null) {
                specification = specification.and(buildSpecification(criteria.getNiveauEtude(), Contrat_.niveauEtude));
            }
            if (criteria.getTypeContrat() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeContrat(), Contrat_.typeContrat));
            }
        }
        return specification;
    }
}
