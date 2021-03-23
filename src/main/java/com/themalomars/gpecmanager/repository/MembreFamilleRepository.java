package com.themalomars.gpecmanager.repository;

import com.themalomars.gpecmanager.domain.MembreFamille;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MembreFamille entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MembreFamilleRepository extends JpaRepository<MembreFamille, Long>, JpaSpecificationExecutor<MembreFamille> {
}
