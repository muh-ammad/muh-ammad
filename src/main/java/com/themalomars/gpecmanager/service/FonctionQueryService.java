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

import com.themalomars.gpecmanager.domain.Fonction;
import com.themalomars.gpecmanager.domain.*; // for static metamodels
import com.themalomars.gpecmanager.repository.FonctionRepository;
import com.themalomars.gpecmanager.service.dto.FonctionCriteria;

/**
 * Service for executing complex queries for {@link Fonction} entities in the database.
 * The main input is a {@link FonctionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Fonction} or a {@link Page} of {@link Fonction} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FonctionQueryService extends QueryService<Fonction> {

    private final Logger log = LoggerFactory.getLogger(FonctionQueryService.class);

    private final FonctionRepository fonctionRepository;

    public FonctionQueryService(FonctionRepository fonctionRepository) {
        this.fonctionRepository = fonctionRepository;
    }

    /**
     * Return a {@link List} of {@link Fonction} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Fonction> findByCriteria(FonctionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Fonction> specification = createSpecification(criteria);
        return fonctionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Fonction} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Fonction> findByCriteria(FonctionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Fonction> specification = createSpecification(criteria);
        return fonctionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FonctionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Fonction> specification = createSpecification(criteria);
        return fonctionRepository.count(specification);
    }

    /**
     * Function to convert {@link FonctionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Fonction> createSpecification(FonctionCriteria criteria) {
        Specification<Fonction> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Fonction_.id));
            }
            if (criteria.getLibelleFonction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelleFonction(), Fonction_.libelleFonction));
            }
            if (criteria.getSpecialiteId() != null) {
                specification = specification.and(buildSpecification(criteria.getSpecialiteId(),
                    root -> root.join(Fonction_.specialites, JoinType.LEFT).get(Specialite_.id)));
            }
            if (criteria.getEmployeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeId(),
                    root -> root.join(Fonction_.employe, JoinType.LEFT).get(Employe_.id)));
            }
        }
        return specification;
    }
}
