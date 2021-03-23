package com.themalomars.gpecmanager.repository;

import com.themalomars.gpecmanager.domain.Distinction;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Distinction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DistinctionRepository extends JpaRepository<Distinction, Long>, JpaSpecificationExecutor<Distinction> {
}
