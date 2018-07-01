package com.caramelpoint.aeh.repository;

import com.caramelpoint.aeh.domain.Responsable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Responsable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResponsableRepository extends JpaRepository<Responsable, Long> {

}
