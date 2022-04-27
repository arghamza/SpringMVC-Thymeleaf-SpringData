package ma.emsi.patientweb;

import ma.emsi.patientweb.Security.service.SecurityService;
import ma.emsi.patientweb.Security.service.SecurityServiceImpl;
import ma.emsi.patientweb.entities.Medecin;
import ma.emsi.patientweb.entities.Patient;
import ma.emsi.patientweb.repositories.MedecinRepository;
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
    @Bean
    CommandLineRunner commandLineRunner1(PatientRepository patientRepository) {
        return args -> {
            patientRepository.save(new Patient(null, "Hassan", new Date(),true,"Homme","BE102450",115));
            patientRepository.save(new Patient(null, "Fatima", new Date(),false,"Femme","CF485253",260));
            patientRepository.save(new Patient(null, "Hamza", new Date(),true,"Homme","DE915915",342));
            patientRepository.save(new Patient(null, "Farouk", new Date(),false,"Homme","DF475869",233));
            patientRepository.findAll().forEach(p -> {
                System.out.println(p.getNom());
            });
        };
    }
    @Bean
    CommandLineRunner commandLineRunner(MedecinRepository medecinRepository) {
        return args -> {
            medecinRepository.save(new Medecin(null, "Hassan", new Date()));
            medecinRepository.save(new Medecin(null, "Mohammed", new Date()));
            medecinRepository.save(new Medecin(null, "Yassine", new Date()));
            medecinRepository.save(new Medecin(null, "Hanae", new Date()));
            medecinRepository.findAll().forEach(p -> {
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