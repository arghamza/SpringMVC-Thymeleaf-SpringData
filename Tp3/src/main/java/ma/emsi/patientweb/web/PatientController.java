package ma.emsi.patientweb.web;

import lombok.AllArgsConstructor;
import ma.emsi.patientweb.entities.Patient;
import ma.emsi.patientweb.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    public boolean isInteger( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }

    @GetMapping(path = "/user/index")
    public String patients(Model model,@RequestParam(name="size",defaultValue = "5")int size, @RequestParam(name="page",defaultValue = "0") int page, @RequestParam(name="keyword",defaultValue = "") String keyword){
        int score;
        Page<Patient> patients ;

        if(!keyword.equals("") && isInteger(keyword) ) {
            score=Integer.valueOf(keyword) ;
            patients=patientRepository.findByScore(score,PageRequest.of(page , size));
        }
        else if(keyword.equals("Homme") || keyword.equals("Femme")){
            patients=patientRepository.findByGenre(keyword,PageRequest.of(page , size));
        }
        else {
            patients=patientRepository.findByNomContainsOrCINContains(keyword,keyword,PageRequest.of(page , size));
        }
        model.addAttribute("listepatients",patients.getContent());
        model.addAttribute("pages",new int[patients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        model.addAttribute("totalPages",patients.getTotalPages());
        return "patient/patients";
    }

    @GetMapping("/admin/delete")
    public String delete(Long id,String keyword,int page){
        patientRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/")
    public String home (){
        return "home";
    }

    @GetMapping("/user/patients")
    @ResponseBody
    public List<Patient> listPatients(){
        return patientRepository.findAll();
    }


    @GetMapping("/admin/formPatients")
    public  String formPatients(Model model){
        model.addAttribute("patient",new Patient());
        return "patient/formPatients";
    }

    @PostMapping(path="/admin/save")
    public String save(Model model , @Valid Patient patient , BindingResult bindingResult  , @RequestParam(name="keyword" , defaultValue = "") String keyword , @RequestParam(name="page" , defaultValue = "0")int page )
    {
        if(bindingResult.hasErrors())
            return "patient/formPatients" ;

        patientRepository.save(patient) ;
        return "redirect:/user/index?page="+page+"&keyword="+keyword ;
    }
    @GetMapping(path="/admin/editPatient")
    public String editPatient(Model model , Long id ,String keyword , int page )
    {
        Patient p =  patientRepository.findById(id).orElse(null) ;
        if (p==null)
        {
            throw  new RuntimeException("Patient introuvable ") ;
        }
        model.addAttribute("page",page) ;
        model.addAttribute("keyword" , keyword ) ;
        model.addAttribute("patient" ,p) ;
        return "patient/editPatient" ;
    }

}
