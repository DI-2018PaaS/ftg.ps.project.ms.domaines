package ftg.ps.project.ms.domaines.repository;

import ftg.ps.project.ms.domaines.domain.Domaine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Domaine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DomaineRepository extends JpaRepository<Domaine, Long> {

}
