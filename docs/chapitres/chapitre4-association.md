# Mapping des 

# associations 

--
# Mapper les associations

 * On a vu comment mapper et manipuler les classes simples 

 * Comment mapper les relations d’héritage entre les classes 

 * On va voir maintenant un point essentiel : la gestion des liens entre les classes 

 * C’est souvent ici qu’on trouve des pratiques pas très orthodoxes 

--
# Mapper les associations

 * Point très important pour une gestion optimale 

 * JPA contraint à de bonnes pratiques pour les associations → Et c’est tant mieux \! 

 * Conseil : Respecter la logique objet  

 * →     Pas d'attributs \- clefs étrangères \!\!\! 

--
# Mapper les associations

 * Matérialiser au maximum les liens objets 

 * Règle générale : Modèle objet « propre »  

 * →     Utilisation d'Hibernate facilitée 

 * JPA va permettre de « naviguer » dans le graphe objet sans se préoccuper des accès BDD (enfin un peu quand même 😉) 

--
# Mapper les associations

 * JPA couvre toutes les associations possibles en termes de cardinalité 

 * Certaines limites pour des associations avec polymorphisme 

 * Avant tout\, bien définir les cardinalités de l'assocation 

 * Point important : Bien choisir le type de Collection pour les cardinalités multiples 

--
# Différents types d'association

*  Types d'associations possibles : 
    *  @OneToMany : Relation (1 \- \*) 
    *  @ManyToOne : Relation (\* \- 1) 
    *  @OneToOne : Relation (1 \- 1) 
    *  @ManyToMany : Relation (\* \- \* ) 
    *  @Any\, @ManyToAny : Utilisée pour le polymorphisme (pas abordée ici) 
*  Premier mot : Se réfère à « la classe où je suis » 
*  Second mot : concerne l'autre bout de la relation\, « la classe vers laquelle l’assocation pointe » 

--
# Choisir le type de 
# Collection adéquat

 * Question à se poser : quelles règles ma Collection devra respecter ? 

 * Collection d'objets uniques → Utiliser les Set \! 

 * Collection ordonnée mais sans index → SortedSet 

--
# Choisir le type de
# Collection adéquat

 * Ne pas utiliser systématiquement les List\, seulement si réel besoin d'un index 

 * Les Set permettent de garantir l'unicité → Utile à Hibernate pour constituer les objets issus des requêtes 

 * Conseil perso : Faire simple avec un Set puis gérer en Java pour les besoins plus complexes 

--
# Assocation @OneToMany (1 - *)

![](./img/diapo_formation_hibernate_5_1.png) <!-- .element: class="image-large" -->

--
# Assocation @OneToMany (1 - *)

![](./img/diapo_formation_hibernate_5_2.png) <!-- .element: class="image-large" -->

--
# Assocation @OneToMany (1 - *)

![](./img/diapo_formation_hibernate_5_3.png) <!-- .element: class="image-large" -->


--
# Assocation @OneToMany (1 - *)

 * L'attribut doit donc être une Collection 

 * Par défaut\, JPA génère une table d'association (pourquoi ?) 

 * Il faut ajouter @JoinColumn pour que JPA gère l'association avec une clef étrangère 

 * Le nom de la colonne est précisé dans son attribut « name » 

 * On peut préciser avec @ForeignKey le nom de la contrainte clef étrangère\, son unicité etc\. 

--
# Assocation @ManyToOne (* - 1)

 * Relation exactement inverse\, on change le « propriétaire » 

 * On définit de même la jointure avec @JoinColumn 

 * Le sens de navigation à travers le graphe objet change 

 * La représentation en BDD est la même 

 * C’est la matérialisation côté Java qui change 

--
# Sens de navigation
# et relation bidirectionnelle

*  Dans chaque exemple\, 1 seul sens de navigation possible → Bien choisir dès le départ \!\!\! 
*  Il est possible de définir des relations « bidirectionnelles » 
*  Débat sur utilisation de relations unidirectionnelles ou bidirectionnelles 
*  Problème de gestion de la cohérence entre les 2 extrémités 

--
# Sens de navigation
# et relation bidirectionnelle

*  Débat sur la gestion des associations : 
    *  Certains développeurs conseillent d'éviter le bidirectionnelle 
    *  Documentation Hibernate → Systématiser le bidirectionnel (plutôt mon avis) 

--
# Relation bidirectionnelle :
# @OneToMany @ManyToOne

![](./img/diapo_formation_hibernate_5_4.png) <!-- .element: class="image-large" -->

--
# Relation bidirectionnelle :
# @OneToMany @ManyToOne

![](./img/diapo_formation_hibernate_5_5.png) <!-- .element: class="image-large" -->

--
# Finalement...

*  …     Faites votre expérience \! 
*  Avis personnel : Bidirectionnalité sauf certitude d'un seul sens de navigation nécessaire 

--
# Finalement...

*  Inconvénient : plus de complexité pour gérer la cohérence : 
    *  Ajouter 2 méthodes : addMaladie\, removeMaladie 
    *  Toujours passer par ces méthodes 
    *  Supprimer le setter de la collection (setMaladies) 
    *  Modifier le getter pour renvoyer une Collection Immutable (Collections\.unmodifiableList/Set/Map) 
*  On touche à une limite du lien entre BDD/Java  
--
# Assocation @OneToOne (1 - 1)

 * Attention : Type d'association non\-normalisée \! 

 * On peut a priori fusionner les tables 

 * Toujours à utiliser avec @JoinColumn 

 * Si utilisation en bidirectionnel\, difficulté pour maîtriser les requêtes (abordé plus loin) 

--
# Assocation @ManyToMany (* - *)

 * Les 2 extrémités doivent être des Collection 

 * Passe obligatoirement par une table d'association 

 * Hibernate utilise des noms de tables et colonnes par défaut 

 * Peuvent se surcharger avec : @JoinTable 

 * Possible en bi\-directionnel (cf exemple) ou en mono\-directionnel 

--
# Relation bidirectionnelle :
# @ManyToMany

![](./img/diapo_formation_hibernate_5_6.png) <!-- .element: class="image-large" -->

--
# Utilisation des Map

*  Les associations peuvent se matérialiser en Java avec des Map 
*  Il faut alors préciser la clef à utiliser avec : 
    *  @MapKeyColumn : en renseignant le nom de la colonne de la table liée  
    *  ou 
    *  @MapKey : en renseignant le nom d'un attribut de la table liée 

--
# Utilisation des Map    

*
    *  @MapKeyEnumerated : si la clef est un enum 
    *  @MapKeyTemporal : si la clef est une date 
    *  @MapKeyJoinColumn : si la clef est un attribut de type Objet de la table liée 
*  Exemple :
```java
@OneToMany(mappedBy="driver")
@MapKeyEnumerated(EnumType.STRING) 
private Map<TypeCar, Car> carMap; 
```

--
# Opérations en Cascade

*  Il est possible de propager sur les « enfants » les opérations faîtes sur un objet « parent » (ie porteur de l’association) 
*  Exemple : Suppression du parent → suppression des enfants 
*  Ce comportement se configure avec l'attribut Cascade des annotations d'associations\. Ex :      @OneToMany    (cascade=CascadeType\.    ALL    ) 

--
# Opérations en Cascade

*  Choix des opérations à propager : 
    *  CascadeType\.ALL : Toutes les opérations 
    *  CascadeType\.PERSIST : Opérations persist 
    *  CascadeType\.REMOVE : Opérations remove 
    *  … 

--
# Opérations en Cascade

 * Se poser la question : Les enfants ont\-ils une existence indépendante de leur parent ? 

 * Cas de figure fréquent où les objets\-enfants non pas vocation à exister indépendamment 

--
# Opérations en Cascade

 * Attribut     orphanRemoval     : permet de signifier à Hibernate qu’il doit supprimer les éléments « orphelins » 

 * Les éléments supprimés d’une collection seront donc supprimés de la base aussi (DELETE) 

 * Le formateur décline toutes responsabilités dans l’application de ces principes dans le monde réel… 🤪   

--
# TP 5 : Mapper les associations (1/2)

*  Mapper les liens bidirectionnels entre : 
    *  Entreprise → Secteur 
    *  Indice → Secteur 
    *  Entreprise → Declaration 
*  Lancer TestGenerationScriptClass : comparer le script obtenu avec celui fourni 

![](./img/diapo_formation_hibernate_5.png)

--
# TP 5 : Mapper les associations (2/2)

*  On suppose que les déclarations dépendent des entreprises\, et les indices des secteurs\. Positionner l’option « cascade » aux endroits où elle est pertinente 
*  Vérifier le fonctionnement avec  MappingAssociationTest 
*  Supprimer le @Transactionnal sur     testSecteur() 
*  Quelle exception constatez\-vous ? 

![](./img/diapo_formation_hibernate_5.png)
