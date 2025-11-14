# Réaliser les 

# opérations CRUD 

--
# Les opérations CRUD via JPA

 * On a vu comment lier une classe\, à son équivalent dans la BDD 

 * On va voir maintenant comment effectuer les opérations de base sur cette table 

 * JPA permet d’effectuer toutes ces opérations en langage\-objet\, sans écrire de SQL 

--

# Les opérations CRUD via JPA

 * Principe de JPA : les opérations CRUD se font à travers les objets Java 

 * JPA offre un ensemble de classes et méthodes permettant de réaliser ces opérations basiques 

 * Couche d'abstraction entre le code métier et la persistance des données  

 * Effet aspirant : Il vaudra mieux ensuite passer au maximum par les mécanismes de JPA 

--
# EntityManager : 
# classe centrale de JPA

*  Classe par qui s'effectue l'ensemble des opérations JPA
*  Méthodes basiques: 
    *  `public         void     persist(Object     entity    )` :     Permet de rendre persistant un objet nouvellement créé en Java → traduction SQL : INSERT 

--
# EntityManager : 
# classe centrale de JPA

*
    *  `public     <T> T find(Class<T>     entityClass    , Object     primaryKey    )` :     Permet de récupérer un objet via son identifiant depuis la base → traduction SQL : SELECT\. La classe de l’entité correspondante doit être spécifiée 

--
# EntityManager : 
# classe centrale de JPA

*
    *  `public         void     remove(Object     entity    )` :     Permet de supprimer un objet persistant → traduction SQL : DELETE 
    *  Les UPDATEs se font eux de manière transparente\, sans déclaration explicite (cf diapos suivantes) 
*  Durée de vie par défaut de l’Entity Manager =     durée de la transaction 
--
# Fonctionnement classique

 * JPA tâche d'être le moins présent possible au sein du code métier

 * Récupération des objets via l'EntityManager 

 * Modifications directement faites sur les objets Java et leurs attributs 

 * À la fin de la transaction : l'EntityManager « envoie » l'ensemble de ces modifications à la base\, via des instructions SQL 

 * On appelle cette opération le     flush() 

--
# Schéma du fonctionnement
# classique

![](./img/diapo_formation_hibernate_3_1.png) <!-- .element: class="image-large" -->

--
# Exemple

![](./img/diapo_formation_hibernate_3_2.png) <!-- .element: class="image-large" -->

--
# Pattern « Unit of Work »

 * Pour les UPDATES\, on voit qu'il n'est pas nécessaire de spécifier explicitement à JPA les objets à mettre à jour 

 * Par défaut\, JPA considère que     toute modification sur un objet qu'il « gère »     (état     managed     : notion vue plus loin)     doit être persistée      

 * Corresponds à la grande majorité des cas 

 * Sinon il faut explicitement « détacher » l'objet de l'EntityManager avec entityManager\.detach(objet) 

--
# TP 3 : Réaliser les opérations CRUD

 * Définir     EntrepriseDAOJPAImpl     comme implémentation de     EntrepriseDAO 

 * Compléter les méthodes de la classe     EntrepriseDAOJPAImpl 

 * Tester avec     EntrepriseDAOCRUDTest 
 
![](./img/diapo_formation_hibernate_3.png)
