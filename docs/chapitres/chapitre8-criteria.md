# L’API Criteria 

--
# L'API Criteria

* JPA propose une autre manière de réaliser des requêtes complexes 

* Criteria est une API qui permet de construire des requêtes à partir d'objets Java 

* Criteria permet de rendre les requêtes « Type\-Safe »\, ie fortement typée 

--
# L'API Criteria

* Criteria permet de créer des requêtes dynamiques (ajout de clause selon le contexte par ex) 

* Mais requêtes moins lisibles\, construction un peu plus complexe 

* Plusieurs utilisations sont possibles… 

--
# Des requêtes fortement typées

* On a vu avec JPQL\, que les requêtes étaient des chaînes de caractère 

* Mais que se passe\-t\-il si un attribut\, ou une classe change de nom ?… 

* Il faut modifier toutes les requêtes qui y font référence \! 


--
# Des requêtes fortement typées

* Pas d'erreurs de compilation → Couverture par les tests obligatoire ou risque de plantage en prod \!\!\! 

* L'API Criteria permet d'avoir un lien direct\, organique\, entre les requêtes et les objets sur lesquels elles portent 

* On a un typage complet des requêtes elles-mêmes qui offre un niveau de sécurité supplémentaire
 
--
# Des requêtes fortement typées

* Autre avantage : Dans le contexte de requête avec des filtres qui doivent évoluer dynamiquement (ex: tableau avec filtre sélectionnables)

* Comment ajouter proprement des conditions aux requêtes selon le contexte sans manipuler des String ? 
   * →    Criteria permet la création de requêtes via la manipulation d’objets 

* On évite la manipulation fastidieuse et peu maintenable de chaîne de caractères

--
# Exemple :

![](./img/diapo_formation_hibernate_8_1.png) <!-- .element: class="image-large" -->

--
# Exemple :

![](./img/diapo_formation_hibernate_8_2.png) <!-- .element: class="image-large" -->
--
# Exemple :

![](./img/diapo_formation_hibernate_8_3.png) <!-- .element: class="image-large" -->
--
# Exemple :

![](./img/diapo_formation_hibernate_8_4.png) <!-- .element: class="image-large" -->
--
# Exemple :

![](./img/diapo_formation_hibernate_8_5.png) <!-- .element: class="image-large" -->
--
# Exemple :

![](./img/diapo_formation_hibernate_8_6.png) <!-- .element: class="image-large" -->

--
# Exemple :
*  Equivalent JPQL : 
```sql
SELECT secteur FROM Secteur 
WHERE codeNaf = :paramCodeNaf
```
* Et oui le désavantage de Criteria est sa verbosité 😉 <!-- .element: class="fragment" -->
--
# Classe-Renommage résilience

* Ainsi si la classe Secteur est renommée SecteurNaf, la requête reste valide

* On voit qu’on pourrait ajouter ou enlever des conditions au
 WHERE programmatiquement 

* Utile pour les tableaux avec filtres dynamiques

--
# Attribut-Renommage 
# non-résilience

* En revanche il reste les attributs (dans les critères)\, qui restent non-typés : 
```java
criteria.where(builder.equal(root.get("codeNaf"), codeNaf)); 
```
* Il est possible d'utiliser le « JPA Metamodel Generator » 

* Permets de générer des classes qui sont l'image du mapping\, et d'y faire référence dans les requêtes Criteria 

--
# JPA Metamodel Generator

* Classes générées par JPA Metamodel Generator : 
```java
@Generated(value = 
        "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Secteur.class)
public abstract class Secteur_ {
  public static volatile 
        SetAttribute<Secteur, Indice> indices;
  public static volatile 
        SingularAttribute<Secteur, String> libelleNomenclature;
  public static volatile 
        SingularAttribute<Secteur, String> codeNaf;
  public static volatile 
        SetAttribute<Secteur, Entreprise> entreprises;
  public static volatile 
        SingularAttribute<Secteur, Integer> id;
}
```

--
# JPA Metamodel Generator

* Les attributs des classes générées sont statiques 

* On peut donc dans les requêtes faire référence aux attributs en tant qu'objet  : 
```java
criteria.where(builder.equal(root.get(Secteur_.codeNaf), codeNaf)); 
```
* On arrive donc à des requêtes entièrement typées 

* Pour générer les classes du métamodèle\, un peu de config Maven \+ plugin IDE (ex: Eclipse « m2e »)

* Les classes générées se mettent à jour automatiquement lorsque les entités évoluent 

--
# L'API Criteria : Conclusion

* L'API Criteria permet d'avoir des requêtes totalement typées 

* Assez utile pour générer des requêtes dynamiques qui s’adaptent au contexte (tableau avec filtres optionnels) 

* Difficulté réside dans son appropriation, sa verbosité et son manque de lisibilité 

* <u>Conseil :</u> Si utilisation de Criteria\, alors utilisation complète     avec Métamodel 

--
# TP 8 : Requête de chargement 
# complexe avec Criteria

* Réaliser une méthode de récupération d'un secteur avec ses entreprises et leur déclaration avec l'API Criteria 

* Lancer le test qui vérifie qu'il n'y a qu'une     requête (    SecteurServicesPerformancesTestCriteria    ) 

* Observer les requêtes générées avec show_sql=true 

![](./img/diapo_formation_hibernate_14.png)
