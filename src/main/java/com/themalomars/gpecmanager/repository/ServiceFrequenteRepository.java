package com.themalomars.gpecmanager.repository;

import com.themalomars.gpecmanager.domain.ServiceFrequente;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ServiceFrequente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceFrequenteRepository extends JpaRepository<ServiceFrequente, Long>, JpaSpecificationExecutor<ServiceFrequente> {
}
