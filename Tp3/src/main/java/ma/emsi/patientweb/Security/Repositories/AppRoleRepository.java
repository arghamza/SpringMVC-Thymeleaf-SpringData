package ma.emsi.patientweb.Security.Repositories;

import ma.emsi.patientweb.Security.Entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {
    AppRole findByRoleName(String roleName);
}
