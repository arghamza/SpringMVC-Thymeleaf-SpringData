package ma.emsi.patientweb.Security.Repositories;

import ma.emsi.patientweb.Security.Entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AppUserRepository extends JpaRepository<AppUser,String> {
    AppUser findByUsername(String username);
}
