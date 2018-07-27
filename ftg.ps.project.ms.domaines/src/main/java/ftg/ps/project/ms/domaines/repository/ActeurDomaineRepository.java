package ftg.ps.project.ms.domaines.repository;

import ftg.ps.project.ms.domaines.domain.ActeurDomaine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ActeurDomaine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActeurDomaineRepository extends JpaRepository<ActeurDomaine, Long> {

}
