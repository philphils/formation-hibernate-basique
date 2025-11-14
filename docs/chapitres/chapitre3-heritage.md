# Mapper les relations 

# d’héritage 

--
# Mapping de l'héritage

 * On a vu comment mapper une classe (sans lien entre les classes) 

 * Puis comment réaliser les opérations de base sur cette classe 

 * On va maintenant s’attaquer à une notion importante en Java : l’héritage 

 * On va voir comment traduire les relations d’héritage en BDD via le mapping JPA 

--
# Mapping de l'héritage

*  JPA offre plusieurs possibilités pour mapper les relations d'héritage 
*  Au préalable se poser la question : Doit\-on utiliser l'héritage ?     (https://www\.youtube\.com/watch?v=wfMtDGfHWpA) 
*  4 grandes « stratégies » proposées par Hibernate 
*  Questions à se poser pour choisir : 
    *  Vais\-je utiliser le polymorphisme ?     (requête directement sur la classe mère) 
    *  Quelle est la volumétrie ?     (performances) 
--
# Mapping de l'héritage

 * 4 grandes « stratégies » :  

    * @MappedSuperclass 

    * @Inheritance strategy = JOINED 

    * @Inheritance strategy = SINGLE\TABLE 

    * @Inheritance strategy = TABLE\PER\CLASS 

--
# @MappedSuperclass

 * Permet seulement la mise en commun d'attribut →     pas de requête polymorphique 

 * Se déclare au niveau de la (ou des) classe mère 

 * Les attributs s'ajoutent dans les tables des entités filles 

 * Les attributs hérités sont pris en compte par JPA 

--
 # @MappedSuperclass 

![](./img/diapo_formation_hibernate_4_1.png) <!-- .element: class="image-large" -->

--
 # @MappedSuperclass 

![](./img/diapo_formation_hibernate_4_2.png) <!-- .element: class="image-large" -->
--
 # @MappedSuperclass 

![](./img/diapo_formation_hibernate_4_3.png) <!-- .element: class="image-large" -->
--
 # @MappedSuperclass 

![](./img/diapo_formation_hibernate_4_4.png) <!-- .element: class="image-large" -->

--
# Les héritages de type
# @Inheritance

 * Tous les autres type de mapping de l'héritage permettent les requêtes sur les classes mères (ie polymorphiques) 

 * Différence au niveau du modèle en base de données 

 * Différence au niveau des requêtes polymorphiques générées 

 * Arbitrage entre performances et normalisation 

--
# @Inheritance stratégie
# SINGLETABLE

*  Toutes les implémentations sont stockées dans la même table (stratégie par défaut) 
*  Utilisation d'une colonne (DTYPE par défaut) comme discriminant pour connaître l’entité correspondante (possible redéfinition avec @DiscriminatorColumn) 

--
# @Inheritance stratégie
# SINGLETABLE

*  Désavantage : Nombre de colonnes plus important car des colonnes à null : 
    *  Schéma dénormalisé 
    *  Perte d'espace 
*  Avantage : Les requêtes polymorphiques n'utilisent qu'une table\, pas de jointure 
*  Intéressant si peu de différence entre les sous\-classes 

--
# @Inheritance stratégie
# SINGLETABLE

![](./img/diapo_formation_hibernate_4_5.png) <!-- .element: class="image-large" -->

--
# @Inheritance stratégie
# SINGLETABLE

![](./img/diapo_formation_hibernate_4_6.png) <!-- .element: class="image-large" -->
--
# @Inheritance stratégie
# SINGLETABLE

![](./img/diapo_formation_hibernate_4_7.png) <!-- .element: class="image-large" -->

--
# @Inheritance stratégie JOINED

 * Chaque classe est mappée vers une table\, y compris la classe mère 

 * La clef primaire des sous\-classes\, est aussi une clef étrangère vers la ligne de la classe mère 

 * Requête polymorphique réalisée à l'aide d'une jointure 

--
# @Inheritance stratégie JOINED

 * Schéma normalisé  →  🌝

 * Désavantage : multiplication des jointures  

 * →     avec un volume important\, les jointures peuvent être coûteuses en perfs 

--
# @Inheritance stratégie JOINED


![](./img/diapo_formation_hibernate_4_8.png) <!-- .element: class="image-large" -->


--
# @Inheritance stratégie JOINED


![](./img/diapo_formation_hibernate_4_9.png) <!-- .element: class="image-large" -->

--
# @Inheritance stratégie JOINED


![](./img/diapo_formation_hibernate_4_10.png) <!-- .element: class="image-large" -->

--
# @Inheritance stratégie JOINED


![](./img/diapo_formation_hibernate_4_11.png) <!-- .element: class="image-large" -->


--
# @Inheritance stratégie
# TABLEPERCLASS

 * Aussi connue sous le nom (plus parlant) de table\-per\-concrete\-class 

 * Identique à @MappedSuperclass\, mais autorise les requêtes polymorphiques 

 * Une table par classe concrète      est créée contenant les attributs de la classe mère et ceux hérités  

 * Légère dé\-normalisation → les colonnes communes sont présentes dans chaque table 

--
# @Inheritance stratégie
# TABLEPERCLASS

 * Pas de jointure pour récupérer les objets des classes filles  

 * →     meilleures performances  

 * Pas de colonnes null 

 * Désavantage : Requêtes polymorphiques génèrent un UNION (peu performant) 

--
# @Inheritance stratégie
# TABLEPERCLASS


![](./img/diapo_formation_hibernate_4_12.png) <!-- .element: class="image-large" -->

--
# @Inheritance stratégie
# TABLEPERCLASS


![](./img/diapo_formation_hibernate_4_13.png) <!-- .element: class="image-large" -->

--
# @Inheritance stratégie
# TABLEPERCLASS


![](./img/diapo_formation_hibernate_4_14.png) <!-- .element: class="image-large" -->

--
# @Inheritance stratégie
# TABLEPERCLASS


![](./img/diapo_formation_hibernate_4_15.png) <!-- .element: class="image-large" -->


--
# TP 4 : Mapper l'héritage

 * Avec le script fourni\, deviner le mode d'héritage des classes d'indices qui a été choisi 

 * Réaliser le mapping de la classe     Indice     et de ses filles     IndiceMensuel     et     IndiceAnnuel     avec ce mode (Rappel : on peut utiliser les converter cf diapo 41) 

 * Vérifier la correspondance du script avec TestGenerationScript\.java 

 * Tester avec     IndiceDAOTest

![](./img/diapo_formation_hibernate_4.png)
