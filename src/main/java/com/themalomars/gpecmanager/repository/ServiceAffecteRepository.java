package com.themalomars.gpecmanager.repository;

import com.themalomars.gpecmanager.domain.ServiceAffecte;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ServiceAffecte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceAffecteRepository extends JpaRepository<ServiceAffecte, Long>, JpaSpecificationExecutor<ServiceAffecte> {
}
