# ReadMe.md

![](https://s1.o7planning.com/fr/11545/images/10858756.png)

![](https://img.shields.io/github/followers/arghamza?style=social)  ![](https://img.shields.io/github/issues/arghamza/SpringMVC-Thymeleaf-SpringData) ![](https://img.shields.io/github/issues-pr/arghamza/SpringMVC-Thymeleaf-SpringData) 





# Énoncé 
## Partie 1
### Afficher les Patients
### Faire la Pagination
### Chercher les Patients
### Supprimer un Patient
## Partie 2
### Créer une page Template basée sur Thymeleaf Layout
### Saisir et Ajouter des Patients
### Faire la Validation du Formulaire
### Editer et Mettre à jour un Patient
## Partie 3 & 4
### Ajouter la dépendance Maven de Spring Security
### Personnaliser la configuration de Spring Security
### Basculer de la stratégie authentification
### Basculer vers la stratégie UserDetailsService
## Partie 1
### Afficher les Patients
![](https://github.com/arghamza/SpringMVC-Thymeleaf-SpringData/blob/master/Tp3/img/screens/AfficherPatient.PNG)
```html
<table class="table">
            <thead>
            <tr>
                <th>ID</th><th>Nom</th><th>Date</th><th>Malade</th><th>Score</th>
            </tr>
            </thead>
            <tbody>
            <tr th:each="p:${listepatients}">
                <td th:text="${p.id}"></td>
                <td th:text="${p.nom}"></td>
                <td th:text="${p.dateNaissance}"></td>
                <td th:text="${p.malade}"></td>
                <td th:text="${p.score}"></td>
                <td sec:authorize="hasAuthority('ADMIN')">
                    <a onclick="return confirm('Etes vous sure')" class="btn btn-danger" th:href="@{/admin/delete(id=${p.id},keyword=${keyword},page=${currentPage})}">
                        Delete
                    </a>
                </td>
                <td sec:authorize="hasAuthority('ADMIN')">
                    <a class="btn btn-success" th:href="@{/admin/editPatient(id=${p.id},keyword=${keyword},page=${currentPage})}">
                        Edit
                    </a>
                </td>
            </tr>
            </tbody>
        </table>
```

### Faire la Pagination
```html
<ul class="nav nav-pills">
                <li th:each="page,status:${pages}">
                    <a th:class="${status.index==currentPage?'btn btn-primary ms-1':'btn btn-outline-info ms-1'}"
                       th:text="${status.index}"
                        th:href="@{/user/index(page=${status.index},keyword=${keyword})}"
                    ></a>
                </li>

            </ul>
```
```java
public String patients(Model model,@RequestParam(name="size",defaultValue = "5")int size, @RequestParam(name="page",defaultValue = "0") int page, @RequestParam(name="keyword",defaultValue = "") String keyword){
        Page<Patient> patients=patientRepository.findByNomContains(keyword,PageRequest.of(page,size));
        model.addAttribute("listepatients",patients.getContent());
        model.addAttribute("pages",new int[patients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "patients";
    }
}
```
### Chercher les Patients
![](https://github.com/arghamza/SpringMVC-Thymeleaf-SpringData/blob/master/Tp3/img/screens/Chercher.PNG)
```html
<form method="get" th:action="@{/user/index}">
                <label>Key word</label>
                <input type="text" name="keyword" th:value="${keyword}">
                <button type="submit" class="btn btn-primary">Chercher</button>
            </form>
```
```java
Page<Patient> patients=patientRepository.findByNomContains(keyword,PageRequest.of(page,size));
}
```
### Supprimer un Patient
![](https://github.com/arghamza/SpringMVC-Thymeleaf-SpringData/blob/master/Tp3/img/screens/Delete.PNG)
```html
<td sec:authorize="hasAuthority('ADMIN')">
                    <a onclick="return confirm('Etes vous sure')" class="btn btn-danger" th:href="@{/admin/delete(id=${p.id},keyword=${keyword},page=${currentPage})}">
                        Delete
                    </a>
                </td>
```
```java
@GetMapping("/admin/delete")
    public String delete(Long id,String keyword,int page){
        patientRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }
}
```

## Partie 2
### Créer une page Template basée sur Thymeleaf Layout
```html
<!DOCTYPE html>
<html lang="en"
      xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymleaf/layout"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security"
      layout:decorate="template1"
>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/webjars/bootstrap/5.1.3/css/bootstrap.min.css">
    <title>Index</title>
</head>
<body>
    <div layout:fragment="content1">
    </div>
</body>
</html>
```
### Saisir et Ajouter des Patients
![](https://github.com/arghamza/SpringMVC-Thymeleaf-SpringData/blob/master/Tp3/img/screens/Add.PNG)
```html
<form method="post" th:action="@{/admin/save}">
            <div>
                <label for="nom">
                    Nom
                </label>
                <input id="nom" type="text" name="nom" class="form-control" th:value="${patient.nom}">
                <span class="text-danger" th:errors="${patient.nom}"></span>
            </div>
            <div>
                <label>
                    Date de naissance
                </label>
                <input type="date" name="dateNaissance" class="form-control" th:value="${patient.dateNaissance}">
                <span th:errors="${patient.dateNaissance}"></span>
            </div>
            <div>
                <label>
                    Malade
                </label>
                <input type="checkbox" name="malade" th:checked="${patient.malade}">
                <span th:errors="${patient.malade}"></span>
            </div>
            <div>
                <label>
                    Score
                </label>
                <input type="text" name="score" class="form-control" th:value="${patient.score}">
                <span th:errors="${patient.score}"></span>
            </div>
            <button type="submit" class="btn btn-primary">Save</button>
        </form>
```
### Faire la Validation du Formulaire
```java
 @PostMapping(path="/admin/save")
    public String save(Model model , @Valid Patient patient , BindingResult bindingResult  , @RequestParam(name="keyword" , defaultValue = "") String keyword , @RequestParam(name="page" , defaultValue = "0")int page )
    {
        if(bindingResult.hasErrors())
            return "formPatients" ;

        patientRepository.save(patient) ;
        return "redirect:/user/index?page="+page+"&keyword="+keyword ;
    }
```
### Editer et Mettre à jour un Patient
![](https://github.com/arghamza/SpringMVC-Thymeleaf-SpringData/blob/master/Tp3/img/screens/Edit.PNG)
```html
<form method="post" th:action="@{/admin/save(page=${page} , keyword=${keyword})}">
            <div >
                <label>ID</label>
                <input  class="form-control" type="text" name="id" th:value="${patient.id}">

            </div>
            <div >
                <label>Nom</label>
                <input class="form-control" type="text" name="nom" th:value="${patient.nom}">
                <span  class="text-danger" th:errors="${patient.nom}"/>
            </div>
            <div >
                <label>Date de naissance</label>
                <input class="form-control" type="date" name="dateNaissance" th:value="${patient.dateNaissance}">
                <span class="text-danger" th:errors="${patient.dateNaissance}"/>
            </div>
            <div >
                <label>Malade</label>
                <input  type="checkbox" name="malade" th:checked="${patient.malade}">
                <span class="text-danger" th:errors="${patient.malade}"/>
            </div>
            <div >
                <label>Score</label>
                <input class="form-control" type="text" name="score" th:value="${patient.score}">
                <span class="text-danger" th:errors="${patient.score}"/>
            </div>
            <button type="submit" class="btn btn-primary">Enregistrer</button>
        </form>
```
```java
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
        return "editPatient" ;
    }
```
## Partie 3 & 4
![](https://github.com/arghamza/SpringMVC-Thymeleaf-SpringData/blob/master/Tp3/img/screens/Login.PNG)
### Ajouter la dépendance Maven de Spring Security
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-springsecurity5</artifactId>
        </dependency>
```
### Personnaliser la configuration de Spring Security
```java
String encodedPWD=passwordEncoder.encode("1234");
        auth.inMemoryAuthentication()
                .withUser("user1").password(encodedPWD).roles("USER");
        auth.inMemoryAuthentication()
                .withUser("user2").password(passwordEncoder.encode("12345")).roles("USER");

        auth.inMemoryAuthentication() .withUser("admin").password(passwordEncoder.encode("2546")).roles("USER","ADMIN");
```
```java
protected void configure(HttpSecurity http) throws Exception {
            http.formLogin();
            http.authorizeRequests()
                .antMatchers("/webjars/**").permitAll();
            http.authorizeRequests().antMatchers("/").permitAll();
            http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
            http.authorizeRequests().antMatchers("/user/**").hasRole("USER");
            http.authorizeRequests().anyRequest().authenticated();
            http.exceptionHandling().accessDeniedPage("/403");
    }
```
### Basculer de la stratégie authentification
```java
auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username as principal,password as credentials,active from users where username=?")
                .authoritiesByUsernameQuery("select username as principal, role as role from users_roles where username=?")
                .rolePrefix("ROLE_")
                .passwordEncoder(passwordEncoder);
```
### Basculer vers la stratégie UserDetailsService
```java
auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username as principal,password as credentials,active from users where username=?")
                .authoritiesByUsernameQuery("select username as principal, role as role from users_roles where username=?")
                .rolePrefix("ROLE_")
                .passwordEncoder(passwordEncoder);
```
### Basculer vers la stratégie UserDetailsService
```java
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SecurityService securityService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser=securityService.loadUserByUserName(username);
        /*Collection<GrantedAuthority> authorities=new ArrayList<>();
        appUser.getAppRoles().forEach(role->{
            SimpleGrantedAuthority authority=new SimpleGrantedAuthority(role.getRoleName());
            authorities.add(authority);
        });*/
        Collection<GrantedAuthority> authorities1=
                appUser.getAppRoles()
                        .stream()
                        .map(role->new SimpleGrantedAuthority(role.getRoleName()))
                        .collect(Collectors.toList());
        User user=new User(appUser.getUsername(), appUser.getPassword(),authorities1);
        return user;
    }
}
```

```java
auth.userDetailsService(userDetailsService);
```
## Amélioration
### Recherche multi-critère
![](https://github.com/arghamza/SpringMVC-Thymeleaf-SpringData/blob/master/Tp3/img/screens/Search.PNG)
```java
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
```

### Ajout de entity médecin
![](https://github.com/arghamza/SpringMVC-Thymeleaf-SpringData/blob/master/Tp3/img/screens/medecin.PNG)
```java
public String medecins(Model model,@RequestParam(name="size",defaultValue = "5")int size, @RequestParam(name="page",defaultValue = "0") int page, @RequestParam(name="keyword",defaultValue = "") String keyword){
        Page<Medecin> medecins=medecinRepository.findByNomContains(keyword,PageRequest.of(page,size));
        model.addAttribute("listemedecins",medecins.getContent());
        model.addAttribute("pages",new int[medecins.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        model.addAttribute("totalPages",medecins.getTotalPages());
        return "medecin/medecins";
    }
```
#### edit
![](https://github.com/arghamza/SpringMVC-Thymeleaf-SpringData/blob/master/Tp3/img/screens/edit_medecin.PNG)
```html
<div layout:fragment="content1">
    <div class="col-md-6 offset-3 card mt-3">
        <div class="card-header"> <h1>Editer Medecin</h1></div>
        <div class="card-body">
        <form method="post" th:action="@{/admin/medecin/save(page=${page} , keyword=${keyword})}">
            <div >
                <label><strong>ID</strong></label>
                <input  class="form-control" type="text" name="id" th:value="${medecin.id}">

            </div>
            <div >
                <label><strong>Nom</strong></label>
                <input class="form-control" type="text" name="nom" th:value="${medecin.nom}">
                <span  class="text-danger" th:errors="${medecin.nom}"/>
            </div>
            <div >
                <label><strong>Specialite</strong></label>
                <input class="form-control" type="text" name="specialite" th:value="${medecin.specialite}">
                <span  class="text-danger" th:errors="${medecin.specialite}"/>
            </div>
            <div >
                <label><strong>Date de naissance</strong></label>
                <input class="form-control" type="date" name="dateNaissance" th:value="${medecin.dateNaissance}">
                <span class="text-danger" th:errors="${medecin.dateNaissance}"/>
            </div>
            <button type="submit" class="btn btn-primary mt-3">Enregistrer</button>
        </form>
        </div>
    </div>
</div>
```
### Ajout de entity rendez-vous
![](https://github.com/arghamza/SpringMVC-Thymeleaf-SpringData/blob/master/Tp3/img/screens/RDV.PNG)
```java
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
```
```html
<div class="container mt-2">
    <div class="card">
        <div class="card-header">Liste rendezVous</div>
        <div class="card-body">
            <form method="get" th:action="@{/user/index}">
                <label>Key word</label>
                <input type="text" name="keyword" th:value="${keyword}">
                <button type="submit" class="btn btn-primary">Chercher</button>
            </form>
            <table class="table">
            <thead>
            <tr>
                <th>ID</th><th>Patient</th><th>Medecin</th><th>Date</th>
            </tr>
            </thead>
            <tbody>
            <tr th:each="p:${listerendezVous}">
                <td th:text="${p.id}"></td>
                <td th:text="${p.patient.nom}"></td>
                <td th:text="${p.medecin.nom}"></td>
                <td th:text="${p.dateRDV}"></td>
                <td sec:authorize="hasAuthority('ADMIN')">
                    <a onclick="return confirm('Etes vous sure')" class="btn btn-danger" th:href="@{/admin/rendezVous/delete(id=${p.id},keyword=${keyword},page=${currentPage})}">
                        Delete
                    </a>
                </td>
                <td sec:authorize="hasAuthority('ADMIN')">
                    <a class="btn btn-success" th:href="@{/admin/editRendezVous(id=${p.id},keyword=${keyword},page=${currentPage})}">
                        Edit
                    </a>
                </td>
            </tr>
            </tbody>
        </table>
            <ul class="nav nav-pills pagination">
                <a class="page-link" th:href="@{/user/rendezVous/index(page=(${currentPage}-1!=-1?${currentPage}-1:0),keyword=${keyword})}" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                    <span class="sr-only">Previous</span>
                </a>
                <li class="page-item">
                    <a class="btn btn-primary ms-2 "
                       th:text="${currentPage}"
                    ></a>
                </li>
                <a class="page-link ms-2" th:href="@{/user/rendezVous/index(page=(${currentPage}==${totalPages}-1?${currentPage}:${currentPage}+1),keyword=${keyword})}" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                    <span class="sr-only">Next</span>
                </a>
            </ul>
        </div>
    </div>
</div>
```
#### Amelioration de l'interface
![](https://github.com/arghamza/SpringMVC-Thymeleaf-SpringData/blob/master/Tp3/img/screens/UI.PNG)
----



### End
