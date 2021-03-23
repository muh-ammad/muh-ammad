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

import com.themalomars.gpecmanager.domain.Diplome;
import com.themalomars.gpecmanager.domain.*; // for static metamodels
import com.themalomars.gpecmanager.repository.DiplomeRepository;
import com.themalomars.gpecmanager.service.dto.DiplomeCriteria;

/**
 * Service for executing complex queries for {@link Diplome} entities in the database.
 * The main input is a {@link DiplomeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Diplome} or a {@link Page} of {@link Diplome} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DiplomeQueryService extends QueryService<Diplome> {

    private final Logger log = LoggerFactory.getLogger(DiplomeQueryService.class);

    private final DiplomeRepository diplomeRepository;

    public DiplomeQueryService(DiplomeRepository diplomeRepository) {
        this.diplomeRepository = diplomeRepository;
    }

    /**
     * Return a {@link List} of {@link Diplome} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Diplome> findByCriteria(DiplomeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Diplome> specification = createSpecification(criteria);
        return diplomeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Diplome} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Diplome> findByCriteria(DiplomeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Diplome> specification = createSpecification(criteria);
        return diplomeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DiplomeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Diplome> specification = createSpecification(criteria);
        return diplomeRepository.count(specification);
    }

    /**
     * Function to convert {@link DiplomeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Diplome> createSpecification(DiplomeCriteria criteria) {
        Specification<Diplome> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Diplome_.id));
            }
            if (criteria.getLibelleDiplome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelleDiplome(), Diplome_.libelleDiplome));
            }
            if (criteria.getAnneeDiplome() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAnneeDiplome(), Diplome_.anneeDiplome));
            }
            if (criteria.getEmployeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeId(),
                    root -> root.join(Diplome_.employe, JoinType.LEFT).get(Employe_.id)));
            }
        }
        return specification;
    }
}
