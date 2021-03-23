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

import com.themalomars.gpecmanager.domain.Distinction;
import com.themalomars.gpecmanager.domain.*; // for static metamodels
import com.themalomars.gpecmanager.repository.DistinctionRepository;
import com.themalomars.gpecmanager.service.dto.DistinctionCriteria;

/**
 * Service for executing complex queries for {@link Distinction} entities in the database.
 * The main input is a {@link DistinctionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Distinction} or a {@link Page} of {@link Distinction} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DistinctionQueryService extends QueryService<Distinction> {

    private final Logger log = LoggerFactory.getLogger(DistinctionQueryService.class);

    private final DistinctionRepository distinctionRepository;

    public DistinctionQueryService(DistinctionRepository distinctionRepository) {
        this.distinctionRepository = distinctionRepository;
    }

    /**
     * Return a {@link List} of {@link Distinction} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Distinction> findByCriteria(DistinctionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Distinction> specification = createSpecification(criteria);
        return distinctionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Distinction} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Distinction> findByCriteria(DistinctionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Distinction> specification = createSpecification(criteria);
        return distinctionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DistinctionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Distinction> specification = createSpecification(criteria);
        return distinctionRepository.count(specification);
    }

    /**
     * Function to convert {@link DistinctionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Distinction> createSpecification(DistinctionCriteria criteria) {
        Specification<Distinction> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Distinction_.id));
            }
            if (criteria.getLibelleDistinction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelleDistinction(), Distinction_.libelleDistinction));
            }
            if (criteria.getEmployeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeId(),
                    root -> root.join(Distinction_.employe, JoinType.LEFT).get(Employe_.id)));
            }
        }
        return specification;
    }
}
