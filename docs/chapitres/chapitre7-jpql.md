 # Java Persistence 
 # Query Langage (JPQL) 

--
# Stratégie de chargement : JPQL

 * La configuration au niveau du mapping offre peu de possibilité 

 * Besoin d’une méthode plus souple\, et permettant de composer des requêtes plus poussées 

 * 2 possibilités : JPQL ou Criteria 

--
# Le JPQL

 * JPQL : Il s'agit d'une syntaxe proche du SQL 

 * La syntaxe fait référence aux entités Java mappées avec JPA\, au lieu des tables et colonnes du SQL 

 * Permets de réaliser des requêtes de chargement complexe avec des critères 

--
# Le JPQL

 * On peut réaliser quasiment tous les types de requêtes correspondant au SQL 

 * Certaines restrictions peuvent concerner les UPDATEs\, les INSERTs massifs\, les DELETEs 

 * De nombreuses fonction du SQL sont disponibles (consulter la doc) 

 * Attention :     Faire des requêtes simples autant que possible (conseil perso) 

 * <u>Conseil :</u> Select avec jointure explicite (JOIN\, LEFT JOIN)\, éviter les sous\-requêtes… 

--
# Conseil d'utilisation du JPQL

 * Tant que les volumes restent relativement faibles (performances acceptables) 

 * SELECT : Utiliser le JPQL pour récupérer les données (peu/pas de LazyLoading) 

 * UPDATE : Faire les modifications sur les objets Java via les setters 

 * INSERT\, DELETE :Insérer ou supprimer via l'EntityManager (entityManager\.persist()\, entityManager\.remove()) 

--
# Synthaxe du SELECT

*  Toutes les Table/Entité doivent avoir un alias 
    *  Ex :     
```sql
SELECT med FROM Medecin med -- on omet le AS du SQL
```
*  On parcourt le graphe objet avec le point (\.) en faisant référence aux attributs dans la classe 
    *  Ex :      
```sql
SELECT med FROM Medecin med
WHERE med.nom IS NOT NULL
```

--
# Requêtes paramétrées


* Attention : Ne pas concaténer les paramètres dans la requête (valable hors JPA)  →  <span style="color: red">Risque d'injection SQL !!!</span>
* Il est conseillé de passer des paramètres aux requêtes avec un alias 
    *  Ex : 
    ```sql
    SELECT med FROM Medecin med WHERE med.nom = :paramNom
    ```
*  On renseigne les paramètres ensuite dans l'objet Query (équivalent JPA du PrepareStatement) 
--
# Création des requêtes

*  La création de la requête se fait via l'EntityManager : 
    *  `entityManager.createQuery(requeteHql, TypeRetour.class)`
*  2 objets possibles : 
    *  Query : Sans type retour défini 
    *  TypedQuery : avec type retour (conseillé) 

--
# Création des requêtes

*  On renseigne les paramètres : 
    *  `query.setParameter("paramNom",nom)`
*  Utile : le paramètre peut être un objet ou un tableau (pour les clauses IN) 
    * `query.setParameter("uniteLegale", uniteLegale)`
    * `query.setParameter("listSiren", sirens)`

--
# Récupération des résultats

*  Une fois la requête constituée et les paramètres éventuels renseignés 
*  On peut récupérer les résultats de plusieurs manières : 
    *  `query.getResultList()`     (renvoie une liste d’objets) 
    *  `query.getSingleResult()`     (attends un résultat unique sinon → <span style="color: red">NonUniqueResultException</span> ) 
    *  `query.getResultStream()`     (résultat sous forme de Stream) 

--
# Utilité du Stream

* `getResultList()` et `getSingleResult()` récupère la totalité du ResulSet en une opération 
*  Peut être problématique en cas de volume de données important
* -> <span style="color: red">Risque de surcharge du réseau </span>

--
# Utilité du Stream

*  `getResultStream()` permet d’utiliser la logique Stream 
*  Déclenche la création d’un curseur si cette techno est disponible (dépends du SGBD) 
*  Permet la récupération des données par « paquets » → meilleures performances sur un grand nombre d’objets

--
# Les jointures

*  On peut faire tous les types de jointures : 
    *  Interne :  
    ```sql
    SELECT med FROM Medecin med JOIN med.patients patient
    ```
    *  Externe :
    ```sql
    SELECT med FROM Medecin med LEFT JOIN med.patients patient
    ```
    *  Cartésienne (dites Cross\-Join)(    <span style="color: red">A éviter \!\!\!</span>    ) : 
    ```sql
    SELECT med FROM Medecin med, Patient patient 
    WHERE patient.medecin = med
    ```
--
# Les jointures

*  Hibernate\, d'après le mapping     déduira les colonnes à utiliser pour effectuer les jointures (clef étrangère, id etc.) 
*  Il est préférable d'éviter les jointures cartésiennes, car peu performantes (vrai hors JPA) 
*  On peut ensuite ajouter des critères sur les patients : 
```sql
SELECT med FROM Medecin med 
JOIN med.patients patient 
WHERE patient.nom = :paramNomPatient 
```

--
# Jointures internes

* Les jointures internes ne renvoient les résultats que si la ligne est présente dans la table de jointure
* JPQL :
```sql
SELECT med FROM Medecin med 
JOIN med.patients patient
```

* Requête générée : <!-- .element: class="fragment" -->
```sql
SELECT * FROM medecin
INNER JOIN patient ON patient.idmedecin = medecin.id
``` 
<!-- .element: class="fragment" -->

--

# Jointures externes

* Les jointures externes renvoient tous les résultats y compris si la ligne n'est pas présente dans la table de jointure
* JPQL :
```sql
SELECT med FROM Medecin med 
LEFT JOIN med.patients patient
```

* Requête générée : <!-- .element: class="fragment" -->
```sql
SELECT * FROM medecin
LEFT JOIN patient ON patient.id_medecin = medecin.id
``` 
<!-- .element: class="fragment" -->

--
# Jointures cartésiennes

* Les jointures cartésiennes réalisent un produit des 2 tables avec les lignes vérifiant la condition
* Ces jointures sont <span style="color: red">à éviter</span> car beaucoup moins performantes
* JPQL :
```sql
SELECT med FROM Medecin med, Patient patient 
WHERE patient.medecin = med 
```

* Requête générée : <!-- .element: class="fragment" -->
```sql
SELECT * FROM    medecin, patient 
WHERE patient.id_medecin = medecin.id
``` 
<!-- .element: class="fragment" -->

--
# Chargement des associations

 * On voit donc le besoin des liens objets pour pouvoir réaliser des requêtes "propres". Ex :
```sql
...LEFT JOIN med.patients patient
```
* → cf. Besoin d’un modèle objet « propre » 
* JPQL permet de récupérer les objets avec leurs associations instanciées

--
# Chargement des associations


 * On peut récupèrer des « portions » du graphe objet → Permet d'éliminer le problème du « Select N\+1 » 

 * Possible avec les jointures internes ou externes 

 * Utilisation du mot clef :     `FETCH`

 * Attention :     Limite du nb de jointures si la Collection est une List  → Privilélier les Set (cf partie sur les associations) 

--
# Chargement des associations

*  Par exemple\, si on veut récupérer le Medecin avec ses Patient(s) instanciés : 
```sql
SELECT med FROM Medecin med
JOIN FETCH med.patients patient
```
*  JPA retournera donc un objet Medecin, <span style="color: red">avec leur collection de Patient</span>
* On a ici une jointure interne donc que les Medecin qui ont au moins un Patient

--
# Chargement des associations

*  On peut aussi faire une jointure externe :
```sql
SELECT med FROM Medecin med
LEFT JOIN FETCH med.patients…
```
*  On aura alors aussi les médecins qui n'ont pas de patients
*  Sans le mot `FETCH`, la requête contiendra bien la jointure\, mais la collection de patients restera vide 
*  Les jointures sans `FETCH` restent utiles pour appliquer des conditions         (ex\. diapo suivante) 

--
# Chargement des associations

*  Comme on a vu\, on peut ajouter des critères sur les objets de l'association : 
```sql
SELECT med FROM Medecin med 
JOIN FETCH med.patients patient 
WHERE patient.nom = :paramNomPatient
```
*  On récupère alors un Medecin mais les patients instanciés sont seulement ceux qui vérifient les critères du `WHERE`

--
# Chargement des associations 
# et conditions

*  <span style="color: red">Attention !</span> Ici on aura les Medecin avec dans leurs collections de patients uniquement les patients dont le nom vaut `paramNomPatient`
*  L'objet est donc « incomplet » → Peut conduire à des erreurs...

--
# Chargement des associations 
# et conditions

*  <u>Conseil :</u> Si les objets liés sont relativement « peu nombreux », charger tous les objets liés et faire le tri en Java
*  L'objet est à l'image du contenu de la BDD
* Moins de surprises à l'exécution et code plus maintenable

--
# Vérification synthaxique

 * Les requêtes sont analysées à l'exécution → Potentielles erreur sur le nom des classes ou attributs 

 * On peut utiliser `@NamedEntityGraph`  ou `@NamedQuery` pour stocker au niveau des entités les requêtes 

 * Elles sont alors analysées au démarrage du serveur 

 * Moins souple que JPQL puisque défini au sein du mapping 

--
# Vérification synthaxique

* Spring-Data est un module Spring-Boot encapsulant Hibernate/JPA
* Avec Spring-Data les requêtes JPQL définies avec `@Query` sont analysées au démarrage du serveur → Plus de risque d'erreur de synthaxe non identifiée 😊
 * Cf partie sur Spring-Data en fin de présentation

--
# Résolution du "SELECT N+1"

 * On peut donc définir avec JPQL des portions de graphe qu'on va charger en 1 fois 

 * Il vaut mieux charger tout ce dont on a besoin en 1 fois… mais que ce dont on a besoin \! 

 * Cela peut conduire à des requêtes longues (nb caractères)\, mais le traitement est en général plus court 

 * N'hésitez pas à tester pour s'en assurer \! 

 * <span style="color : red">Attention :</span>     Ne pas oublier les indexs sur les clefs étrangères pour Postgres \!\!\! 

--
# Ex : Traitement du dossier médical
# des patients d'un médecin 

![](./img/diapo_formation_hibernate_8.png) <!-- .element: class="image-large" -->

--
# Ex : Traitement du dossier médical
# des patients d'un médecin 

![](./img/diapo_formation_hibernate_9.png) <!-- .element: class="image-large" -->

--
# Ex : Traitement du dossier médical
# des patients d'un médecin 

![](./img/diapo_formation_hibernate_10.png) <!-- .element: class="image-large" -->

--
# Ex : Traitement du dossier médical
# des patients d'un médecin 

![](./img/diapo_formation_hibernate_11.png) <!-- .element: class="image-large" -->

--
# Ex : Traitement du dossier médical
# des patients d'un médecin 

![](./img/diapo_formation_hibernate_12.png) <!-- .element: class="image-large" -->

--
# Ex : Traitement du dossier médical
# des patients d'un médecin 

* Requête JPQL correspondante :
```sql
SELECT med FROM Medecin med 
JOIN FETCH med.consultations consult 
JOIN FETCH consult.patient patient 
JOIN FETCH patient.dossierMedical dossier 
WHERE med.id = :paramId
```
*  On récupère en une fois le médecin avec tous les objets liés nécessaires 
--
# Ex : Traitement du dossier médical
# des patients d'un médecin 

* Objectif : On ne doit avoir ensuite aucune requête générée au cours du traitement (`show_sql=true`)
* Les associations entre objets sont nécessaires au parcours du graphe → D'où la nécessité de les matérialiser (cf partie association) 

--
# De nombreuses autres possibilités

*  Possibilités de faire des « projections » : 
```sql
SELECT med.nom, med.age FROM Medecin med
```
*  `getResultList()` renvoie alors une List de tableau d'objet (`List<Object[]>`) 
    *  Dans l'exemple : 
        * `object[0]` → nom
        * `object[1]` → age 
*  <u>Conseil :</u> Travailler autant que possible avec des objets complets 
*  Utile si lenteurs à cause de trop de colonnes
--
# Autres fonctions

*  Fonctions d'agrégation disponibles : 
    *  COUNT\, AVG\, MIN\, MAX\, SUM 
*  Possibilité de faire des     GROUP BY     et     ORDER BY 
*  Fonction chaîne de caractère : 
    *  CONCAT\, SUBSTRING\, UPPER\, LENGTH…  
*  Fonction mathématique : 
    *  ABS\, MOD\, SQRT …  
*  Fonction date : 
    *  CURRENT\DATE/TIME/TIMESTAMP 
    *  YEAR\, MONTH\, DAY …     (propres à Hibernate)
--
# Autres fonctions

*  Liste complète dans la doc : https://docs.hibernate.org/orm/7.1/userguide/html_single/
*  Conseil perso : Sauf besoin particulier (ex\. tableau de bord)\, se limiter plutôt à des requêtes simples et faire les traitements en Java

--
# TP 7 : Requête de
# chargement complexe (1/2)

 * Ecrire le contenu de la méthode `findByCodeNafWithEntreprisesAndDeclarationAndIndicesJPQL` dans `SecteurDaoImpl`
 * Objectif : Récupérer un secteur avec ses entreprises et leurs déclarations 

![](./img/diapo_formation_hibernate_13.png)

--
# TP 7 : Requête de
# chargement complexe (2/2)

 * Lancer le test `SecteurServicesPerformancesTestJPQL`

 * Observer les requêtes générées avec `show_sql=true`

 * Examiner le code du test et la méthode pour vérifier qu’il n’y a qu’une requête générée 

![](./img/diapo_formation_hibernate_13.png)
