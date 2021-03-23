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

import com.themalomars.gpecmanager.domain.Employe;
import com.themalomars.gpecmanager.domain.*; // for static metamodels
import com.themalomars.gpecmanager.repository.EmployeRepository;
import com.themalomars.gpecmanager.service.dto.EmployeCriteria;

/**
 * Service for executing complex queries for {@link Employe} entities in the database.
 * The main input is a {@link EmployeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Employe} or a {@link Page} of {@link Employe} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeQueryService extends QueryService<Employe> {

    private final Logger log = LoggerFactory.getLogger(EmployeQueryService.class);

    private final EmployeRepository employeRepository;

    public EmployeQueryService(EmployeRepository employeRepository) {
        this.employeRepository = employeRepository;
    }

    /**
     * Return a {@link List} of {@link Employe} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Employe> findByCriteria(EmployeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Employe> specification = createSpecification(criteria);
        return employeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Employe} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Employe> findByCriteria(EmployeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Employe> specification = createSpecification(criteria);
        return employeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Employe> specification = createSpecification(criteria);
        return employeRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Employe> createSpecification(EmployeCriteria criteria) {
        Specification<Employe> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Employe_.id));
            }
            if (criteria.getMatricule() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMatricule(), Employe_.matricule));
            }
            if (criteria.getPrenoms() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrenoms(), Employe_.prenoms));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Employe_.nom));
            }
            if (criteria.getIntituleEmploye() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIntituleEmploye(), Employe_.intituleEmploye));
            }
            if (criteria.getDateNaissance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateNaissance(), Employe_.dateNaissance));
            }
            if (criteria.getLieuNaissance() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLieuNaissance(), Employe_.lieuNaissance));
            }
            if (criteria.getNumeroTelephone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroTelephone(), Employe_.numeroTelephone));
            }
            if (criteria.getAdresse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdresse(), Employe_.adresse));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Employe_.email));
            }
            if (criteria.getDateEmbauchement() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateEmbauchement(), Employe_.dateEmbauchement));
            }
            if (criteria.getDateRetraite() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateRetraite(), Employe_.dateRetraite));
            }
            if (criteria.getAge() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAge(), Employe_.age));
            }
            if (criteria.getNumeroCni() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroCni(), Employe_.numeroCni));
            }
            if (criteria.getNumeroIpres() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroIpres(), Employe_.numeroIpres));
            }
            if (criteria.getNumeroCss() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroCss(), Employe_.numeroCss));
            }
            if (criteria.getGroupeSanguin() != null) {
                specification = specification.and(buildSpecification(criteria.getGroupeSanguin(), Employe_.groupeSanguin));
            }
            if (criteria.getSituationMatrimonial() != null) {
                specification = specification.and(buildSpecification(criteria.getSituationMatrimonial(), Employe_.situationMatrimonial));
            }
            if (criteria.getSexe() != null) {
                specification = specification.and(buildSpecification(criteria.getSexe(), Employe_.sexe));
            }
            if (criteria.getDisponibilite() != null) {
                specification = specification.and(buildSpecification(criteria.getDisponibilite(), Employe_.disponibilite));
            }
            if (criteria.getContratId() != null) {
                specification = specification.and(buildSpecification(criteria.getContratId(),
                    root -> root.join(Employe_.contrat, JoinType.LEFT).get(Contrat_.id)));
            }
            if (criteria.getDiplomeId() != null) {
                specification = specification.and(buildSpecification(criteria.getDiplomeId(),
                    root -> root.join(Employe_.diplomes, JoinType.LEFT).get(Diplome_.id)));
            }
            if (criteria.getFonctionId() != null) {
                specification = specification.and(buildSpecification(criteria.getFonctionId(),
                    root -> root.join(Employe_.fonctions, JoinType.LEFT).get(Fonction_.id)));
            }
            if (criteria.getMembreFamilleId() != null) {
                specification = specification.and(buildSpecification(criteria.getMembreFamilleId(),
                    root -> root.join(Employe_.membreFamilles, JoinType.LEFT).get(MembreFamille_.id)));
            }
            if (criteria.getDistinctionId() != null) {
                specification = specification.and(buildSpecification(criteria.getDistinctionId(),
                    root -> root.join(Employe_.distinctions, JoinType.LEFT).get(Distinction_.id)));
            }
            if (criteria.getOperationExterieurId() != null) {
                specification = specification.and(buildSpecification(criteria.getOperationExterieurId(),
                    root -> root.join(Employe_.operationExterieurs, JoinType.LEFT).get(OperationExterieur_.id)));
            }
            if (criteria.getServiceId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceId(),
                    root -> root.join(Employe_.services, JoinType.LEFT).get(ServiceAffecte_.id)));
            }
            if (criteria.getServiceFrequenteId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceFrequenteId(),
                    root -> root.join(Employe_.serviceFrequentes, JoinType.LEFT).get(ServiceFrequente_.id)));
            }
        }
        return specification;
    }
}
