package com.themalomars.gpecmanager.repository;

import com.themalomars.gpecmanager.domain.SecteurActivite;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SecteurActivite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SecteurActiviteRepository extends JpaRepository<SecteurActivite, Long>, JpaSpecificationExecutor<SecteurActivite> {
}
