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

import com.themalomars.gpecmanager.domain.SecteurActivite;
import com.themalomars.gpecmanager.domain.*; // for static metamodels
import com.themalomars.gpecmanager.repository.SecteurActiviteRepository;
import com.themalomars.gpecmanager.service.dto.SecteurActiviteCriteria;

/**
 * Service for executing complex queries for {@link SecteurActivite} entities in the database.
 * The main input is a {@link SecteurActiviteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SecteurActivite} or a {@link Page} of {@link SecteurActivite} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SecteurActiviteQueryService extends QueryService<SecteurActivite> {

    private final Logger log = LoggerFactory.getLogger(SecteurActiviteQueryService.class);

    private final SecteurActiviteRepository secteurActiviteRepository;

    public SecteurActiviteQueryService(SecteurActiviteRepository secteurActiviteRepository) {
        this.secteurActiviteRepository = secteurActiviteRepository;
    }

    /**
     * Return a {@link List} of {@link SecteurActivite} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SecteurActivite> findByCriteria(SecteurActiviteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SecteurActivite> specification = createSpecification(criteria);
        return secteurActiviteRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SecteurActivite} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SecteurActivite> findByCriteria(SecteurActiviteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SecteurActivite> specification = createSpecification(criteria);
        return secteurActiviteRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SecteurActiviteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SecteurActivite> specification = createSpecification(criteria);
        return secteurActiviteRepository.count(specification);
    }

    /**
     * Function to convert {@link SecteurActiviteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SecteurActivite> createSpecification(SecteurActiviteCriteria criteria) {
        Specification<SecteurActivite> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SecteurActivite_.id));
            }
            if (criteria.getLibelleActivite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelleActivite(), SecteurActivite_.libelleActivite));
            }
        }
        return specification;
    }
}
