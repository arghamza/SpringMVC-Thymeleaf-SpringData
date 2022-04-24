package ma.emsi.patientweb;

import ma.emsi.patientweb.Security.service.SecurityService;
import ma.emsi.patientweb.Security.service.SecurityServiceImpl;
import ma.emsi.patientweb.entities.Patient;
import ma.emsi.patientweb.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class PatientWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientWebApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //@Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository) {
        return args -> {
            patientRepository.save(new Patient(null, "Hassan", new Date(), false, 122));
            patientRepository.save(new Patient(null, "Mohammed", new Date(), true, 321));
            patientRepository.save(new Patient(null, "Yassine", new Date(), false, 165));
            patientRepository.save(new Patient(null, "Hanae", new Date(), true, 132));
            patientRepository.findAll().forEach(p -> {
                System.out.println(p.getNom());
            });
        };
    }
    //@Bean
    CommandLineRunner saveUsers(SecurityService securityService){
      return args -> {
          securityService.saveNewUser("mohamed","1234","1234");
          securityService.saveNewUser("yasmine","1234","1234");
          securityService.saveNewUser("hassan","1234","1234");

          securityService.saveNewRole("USER","");
          securityService.saveNewRole("ADMIN","");

          securityService.addRoleToUser("mohamed","USER");
          securityService.addRoleToUser("mohamed","ADMIN");
          securityService.addRoleToUser("yasmine","USER");
          securityService.addRoleToUser("hassan","USER");

      };
    }
}