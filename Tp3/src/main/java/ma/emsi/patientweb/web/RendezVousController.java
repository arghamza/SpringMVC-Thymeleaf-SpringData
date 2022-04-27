package ma.emsi.patientweb.web;

import lombok.AllArgsConstructor;
import ma.emsi.patientweb.entities.Medecin;
import ma.emsi.patientweb.entities.RendezVous;
import ma.emsi.patientweb.entities.RendezVous;
import ma.emsi.patientweb.repositories.MedecinRepository;
import ma.emsi.patientweb.repositories.PatientRepository;
import ma.emsi.patientweb.repositories.RendezVousRepository;
import ma.emsi.patientweb.repositories.RendezVousRepository;
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
public class RendezVousController {
    private RendezVousRepository rendezVousRepository;
    private PatientRepository patientRepository;
    private MedecinRepository medecinRepository;

    @GetMapping(path = "/user/rendezVous/index")
    public String rendezVous(Model model,@RequestParam(name="size",defaultValue = "5")int size, @RequestParam(name="page",defaultValue = "0") int page, @RequestParam(name="keyword",defaultValue = "") String keyword){
        Page<RendezVous> rendezVous=rendezVousRepository.findAll(PageRequest.of(page,size));
        model.addAttribute("listerendezVous",rendezVous.getContent());
        model.addAttribute("pages",new int[rendezVous.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        model.addAttribute("totalPages",rendezVous.getTotalPages());
        return "rendezvous/RendezVous";
    }

    @GetMapping("/admin/rendezVous/delete")
    public String delete(Long id,String keyword,int page){
        rendezVousRepository.deleteById(id);
        return "redirect:/user/rendezVous/index?page="+page+"&keyword="+keyword;
    }


    @GetMapping("/user/rendezVous")
    @ResponseBody
    public List<RendezVous> listrendezVous(){
        return rendezVousRepository.findAll();
    }


    @GetMapping("/admin/formRendezVous")
    public  String formRendezVous(Model model){
        model.addAttribute("rendezVous",new RendezVous());
        return "rendezvous/formRendezVous";
    }

    @PostMapping(path="/admin/rendezVous/save")
    public String save(Model model , @Valid RendezVous rendezVous , BindingResult bindingResult, @RequestParam(name="keyword" , defaultValue = "") String keyword , @RequestParam(name="page" , defaultValue = "0")int page )
    {
        if(bindingResult.hasErrors())
            return "rendezvous/formRendezVous" ;
        rendezVousRepository.save(rendezVous) ;
        return "redirect:/user/rendezVous/index?page="+page+"&keyword="+keyword ;
    }
    @GetMapping(path="/admin/editRendezVous")
    public String editRendezVous(Model model , Long id ,String keyword , int page )
    {
        RendezVous p =  rendezVousRepository.findById(id).orElse(null) ;
        if (p==null)
        {
            throw  new RuntimeException("RendezVous introuvable ") ;
        }
        model.addAttribute("page",page) ;
        model.addAttribute("keyword" , keyword ) ;
        model.addAttribute("rendezVous" ,p) ;

        return "rendezvous/editRendezVous" ;
    }

}
