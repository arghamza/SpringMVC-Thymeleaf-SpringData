package ma.emsi.patientweb.Security.service;

import ma.emsi.patientweb.Security.Entities.AppRole;
import ma.emsi.patientweb.Security.Entities.AppUser;

public interface SecurityService {
    AppUser saveNewUser(String username,String password,String rePassword);
    AppRole saveNewRole(String roleName,String description);
    void addRoleToUser(String username,String roleName);
    void removeRoleFromUser(String username,String roleName);
    AppUser loadUserByUserName(String username);
}
