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

import com.themalomars.gpecmanager.domain.ServiceAffecte;
import com.themalomars.gpecmanager.domain.*; // for static metamodels
import com.themalomars.gpecmanager.repository.ServiceAffecteRepository;
import com.themalomars.gpecmanager.service.dto.ServiceAffecteCriteria;

/**
 * Service for executing complex queries for {@link ServiceAffecte} entities in the database.
 * The main input is a {@link ServiceAffecteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceAffecte} or a {@link Page} of {@link ServiceAffecte} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceAffecteQueryService extends QueryService<ServiceAffecte> {

    private final Logger log = LoggerFactory.getLogger(ServiceAffecteQueryService.class);

    private final ServiceAffecteRepository serviceAffecteRepository;

    public ServiceAffecteQueryService(ServiceAffecteRepository serviceAffecteRepository) {
        this.serviceAffecteRepository = serviceAffecteRepository;
    }

    /**
     * Return a {@link List} of {@link ServiceAffecte} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceAffecte> findByCriteria(ServiceAffecteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceAffecte> specification = createSpecification(criteria);
        return serviceAffecteRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ServiceAffecte} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceAffecte> findByCriteria(ServiceAffecteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceAffecte> specification = createSpecification(criteria);
        return serviceAffecteRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceAffecteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceAffecte> specification = createSpecification(criteria);
        return serviceAffecteRepository.count(specification);
    }

    /**
     * Function to convert {@link ServiceAffecteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ServiceAffecte> createSpecification(ServiceAffecteCriteria criteria) {
        Specification<ServiceAffecte> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ServiceAffecte_.id));
            }
            if (criteria.getCodeService() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCodeService(), ServiceAffecte_.codeService));
            }
            if (criteria.getLibelleService() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelleService(), ServiceAffecte_.libelleService));
            }
            if (criteria.getSecteurActiviteId() != null) {
                specification = specification.and(buildSpecification(criteria.getSecteurActiviteId(),
                    root -> root.join(ServiceAffecte_.secteurActivite, JoinType.LEFT).get(SecteurActivite_.id)));
            }
            if (criteria.getEmployeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeId(),
                    root -> root.join(ServiceAffecte_.employes, JoinType.LEFT).get(Employe_.id)));
            }
        }
        return specification;
    }
}
