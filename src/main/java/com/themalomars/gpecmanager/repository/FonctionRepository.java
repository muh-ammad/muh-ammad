package com.themalomars.gpecmanager.repository;

import com.themalomars.gpecmanager.domain.Fonction;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Fonction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FonctionRepository extends JpaRepository<Fonction, Long>, JpaSpecificationExecutor<Fonction> {
}
