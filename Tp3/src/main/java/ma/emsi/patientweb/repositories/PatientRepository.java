package ma.emsi.patientweb.repositories;

import ma.emsi.patientweb.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    Page<Patient> findByNomContainsOrCINContains(String kw, String cin,Pageable pageable);
    Page<Patient> findByNomOrScore(String keyword, int score, Pageable pageable);
    Page<Patient> findByScore(int score, Pageable pageable);
    Page<Patient> findByGenre(String keyword,Pageable pageable);
    Page<Patient> findByCIN(String keyword,Pageable pageable);
}
