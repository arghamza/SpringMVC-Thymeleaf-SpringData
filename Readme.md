
# ReadMe.md

![](https://s1.o7planning.com/fr/11545/images/10858756.png)

![](https://img.shields.io/github/followers/arghamza?style=social) ![](https://img.shields.io/github/issues/arghamza/SpringMVC-Thymeleaf-SpringData) ![](https://img.shields.io/github/issues-pr/arghamza/SpringMVC-Thymeleaf-SpringData) 


**Table of Contents**

[TOCM]

[TOC]

#Énoncé 
## Partie 1
###Afficher les Patients
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
###Faire la Pagination
```java
function test(){
	console.log("Hello world!");
}
```
###Chercher les Patients
###Supprimer un Patient
```java
function test(){
	console.log("Hello world!");
}
```

##Partie 2
###Créer une page Template basée sur Thymeleaf Layout
###Saisir et Ajouter des Patients
###Faire la Validation du Formulaire
###Editer et Mettre à jour un Patient

##Partie 3 & 4
###Ajouter la dépendance Maven de Spring Security
###Personnaliser la configuration de Spring Security
###Basculer de la stratégie authentification
### Basculer vers la stratégie UserDetailsService


----
















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



###End
