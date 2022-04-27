package ma.emsi.patientweb.repositories;

import ma.emsi.patientweb.entities.Medecin;
import ma.emsi.patientweb.entities.RendezVous;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RendezVousRepository extends JpaRepository<RendezVous,Long> {
}
