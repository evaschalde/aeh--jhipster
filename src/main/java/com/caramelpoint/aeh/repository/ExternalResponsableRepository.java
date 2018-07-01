package com.caramelpoint.aeh.repository;

import com.caramelpoint.aeh.domain.ExternalResponsable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ExternalResponsable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExternalResponsableRepository extends JpaRepository<ExternalResponsable, Long> {

}
