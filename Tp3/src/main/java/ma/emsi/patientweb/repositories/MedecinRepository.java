package ma.emsi.patientweb.repositories;

import ma.emsi.patientweb.entities.Medecin;
import ma.emsi.patientweb.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedecinRepository extends JpaRepository<Medecin,Long> {

    Page<Medecin> findByNomContains(String kw, Pageable pageable);
}
