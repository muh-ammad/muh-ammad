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

import com.themalomars.gpecmanager.domain.MembreFamille;
import com.themalomars.gpecmanager.domain.*; // for static metamodels
import com.themalomars.gpecmanager.repository.MembreFamilleRepository;
import com.themalomars.gpecmanager.service.dto.MembreFamilleCriteria;

/**
 * Service for executing complex queries for {@link MembreFamille} entities in the database.
 * The main input is a {@link MembreFamilleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MembreFamille} or a {@link Page} of {@link MembreFamille} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MembreFamilleQueryService extends QueryService<MembreFamille> {

    private final Logger log = LoggerFactory.getLogger(MembreFamilleQueryService.class);

    private final MembreFamilleRepository membreFamilleRepository;

    public MembreFamilleQueryService(MembreFamilleRepository membreFamilleRepository) {
        this.membreFamilleRepository = membreFamilleRepository;
    }

    /**
     * Return a {@link List} of {@link MembreFamille} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MembreFamille> findByCriteria(MembreFamilleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MembreFamille> specification = createSpecification(criteria);
        return membreFamilleRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link MembreFamille} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MembreFamille> findByCriteria(MembreFamilleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MembreFamille> specification = createSpecification(criteria);
        return membreFamilleRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MembreFamilleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MembreFamille> specification = createSpecification(criteria);
        return membreFamilleRepository.count(specification);
    }

    /**
     * Function to convert {@link MembreFamilleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MembreFamille> createSpecification(MembreFamilleCriteria criteria) {
        Specification<MembreFamille> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MembreFamille_.id));
            }
            if (criteria.getPrenoms() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrenoms(), MembreFamille_.prenoms));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), MembreFamille_.nom));
            }
            if (criteria.getDateNaissance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateNaissance(), MembreFamille_.dateNaissance));
            }
            if (criteria.getLieuNaissance() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLieuNaissance(), MembreFamille_.lieuNaissance));
            }
            if (criteria.getEpoux() != null) {
                specification = specification.and(buildSpecification(criteria.getEpoux(), MembreFamille_.epoux));
            }
            if (criteria.getEpouse() != null) {
                specification = specification.and(buildSpecification(criteria.getEpouse(), MembreFamille_.epouse));
            }
            if (criteria.getEnfant() != null) {
                specification = specification.and(buildSpecification(criteria.getEnfant(), MembreFamille_.enfant));
            }
            if (criteria.getEmployeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeId(),
                    root -> root.join(MembreFamille_.employe, JoinType.LEFT).get(Employe_.id)));
            }
        }
        return specification;
    }
}
