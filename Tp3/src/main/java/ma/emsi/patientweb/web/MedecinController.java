package ma.emsi.patientweb.web;

import lombok.AllArgsConstructor;
import ma.emsi.patientweb.entities.Medecin;
import ma.emsi.patientweb.repositories.MedecinRepository;
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
public class MedecinController {
    private MedecinRepository medecinRepository;

    @GetMapping(path = "/user/medecin/index")
    public String medecins(Model model,@RequestParam(name="size",defaultValue = "5")int size, @RequestParam(name="page",defaultValue = "0") int page, @RequestParam(name="keyword",defaultValue = "") String keyword){
        Page<Medecin> medecins=medecinRepository.findByNomContains(keyword,PageRequest.of(page,size));
        model.addAttribute("listemedecins",medecins.getContent());
        model.addAttribute("pages",new int[medecins.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        model.addAttribute("totalPages",medecins.getTotalPages());
        return "medecin/medecins";
    }

    @GetMapping("/admin/medecin/delete")
    public String delete(Long id,String keyword,int page){
        medecinRepository.deleteById(id);
        return "redirect:/user/medecin/index?page="+page+"&keyword="+keyword;
    }


    @GetMapping("/user/medecins")
    @ResponseBody
    public List<Medecin> listMedecins(){
        return medecinRepository.findAll();
    }


    @GetMapping("/admin/formMedecins")
    public  String formMedecins(Model model){
        model.addAttribute("medecin",new Medecin());
        return "medecin/formMedecins";
    }

    @PostMapping(path="/admin/medecin/save")
    public String save(Model model , @Valid Medecin medecin , BindingResult bindingResult  , @RequestParam(name="keyword" , defaultValue = "") String keyword , @RequestParam(name="page" , defaultValue = "0")int page )
    {
        if(bindingResult.hasErrors())
            return "medecin/formMedecins" ;

        medecinRepository.save(medecin) ;
        return "redirect:/user/medecin/index?page="+page+"&keyword="+keyword ;
    }
    @GetMapping(path="/admin/editMedecin")
    public String editMedecin(Model model , Long id ,String keyword , int page )
    {
        Medecin p =  medecinRepository.findById(id).orElse(null) ;
        if (p==null)
        {
            throw  new RuntimeException("Medecin introuvable ") ;
        }
        model.addAttribute("page",page) ;
        model.addAttribute("keyword" , keyword ) ;
        model.addAttribute("medecin" ,p) ;
        return "medecin/editMedecin" ;
    }

}
