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

import com.themalomars.gpecmanager.domain.OperationExterieur;
import com.themalomars.gpecmanager.domain.*; // for static metamodels
import com.themalomars.gpecmanager.repository.OperationExterieurRepository;
import com.themalomars.gpecmanager.service.dto.OperationExterieurCriteria;

/**
 * Service for executing complex queries for {@link OperationExterieur} entities in the database.
 * The main input is a {@link OperationExterieurCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OperationExterieur} or a {@link Page} of {@link OperationExterieur} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OperationExterieurQueryService extends QueryService<OperationExterieur> {

    private final Logger log = LoggerFactory.getLogger(OperationExterieurQueryService.class);

    private final OperationExterieurRepository operationExterieurRepository;

    public OperationExterieurQueryService(OperationExterieurRepository operationExterieurRepository) {
        this.operationExterieurRepository = operationExterieurRepository;
    }

    /**
     * Return a {@link List} of {@link OperationExterieur} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OperationExterieur> findByCriteria(OperationExterieurCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OperationExterieur> specification = createSpecification(criteria);
        return operationExterieurRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link OperationExterieur} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OperationExterieur> findByCriteria(OperationExterieurCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OperationExterieur> specification = createSpecification(criteria);
        return operationExterieurRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OperationExterieurCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OperationExterieur> specification = createSpecification(criteria);
        return operationExterieurRepository.count(specification);
    }

    /**
     * Function to convert {@link OperationExterieurCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OperationExterieur> createSpecification(OperationExterieurCriteria criteria) {
        Specification<OperationExterieur> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OperationExterieur_.id));
            }
            if (criteria.getLieuOpex() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLieuOpex(), OperationExterieur_.lieuOpex));
            }
            if (criteria.getAnneeOpex() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAnneeOpex(), OperationExterieur_.anneeOpex));
            }
            if (criteria.getEmployeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeId(),
                    root -> root.join(OperationExterieur_.employe, JoinType.LEFT).get(Employe_.id)));
            }
        }
        return specification;
    }
}
