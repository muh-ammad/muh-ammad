package com.themalomars.gpecmanager.repository;

import com.themalomars.gpecmanager.domain.OperationExterieur;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OperationExterieur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperationExterieurRepository extends JpaRepository<OperationExterieur, Long>, JpaSpecificationExecutor<OperationExterieur> {
}
