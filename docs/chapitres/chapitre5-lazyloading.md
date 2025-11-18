# Utilisation du 

# LazyLoading 

--
# Récupérer les associations
# depuis la BDD

*  Ce qui a été vu : 
    *  Mapper les classes et leurs attributs 
    *  Mapper les relations d’héritage 
    *  Récupérer et manipuler une instance d'objets 
    *  Mapper les associations entre les classes 

--
# Nouvelles questions ?

*  Question : Comment récupérer les objets avec leurs associations instanciées ? 
*  Comment parcourir le graphe objet en suivant les liens d’association ? 
*  Exemple : Depuis une entreprise\, récupérer ses déclarations 
--
# Différentes techniques

*  Plusieurs techniques existantes : 
    *  S'appuyer sur le « LazyLoading » 
    *  Faire des requêtes JPQL/HQL 
    *  Faire des requêtes Criteria 
    *  Toujours possibilité de faire du SQL (mais on perd tout l'intérêt du mapping\, déconseillé) 
*  Nous verrons dans cette partie la technique dite du « LazyLoading » 
--
# LazyLoading

 * Comme son nom l'indique\, c'est la technique la moins coûteuse 

 * Attention \!\!\!         C'est aussi la technique qui est souvent source de problèmes de performances 

 * Simple parcours du graphe objet en Java 

 * JPA s'occupe de récupérer les objets liés 

 * Génération transparente de requêtes SQL → A surveiller \! ( `show_sql=true` ) 

--
# LazyLoading : Exemple

![](./img/diapo_formation_hibernate_6_1.png) <!-- .element: class="image-large" -->

--
# LazyLoading : Exemple

![](./img/diapo_formation_hibernate_6_2.png) <!-- .element: class="image-large" -->
--
# LazyLoading : Exemple

![](./img/diapo_formation_hibernate_6_3.png) <!-- .element: class="image-large" -->
--
# LazyLoading : Exemple

![](./img/diapo_formation_hibernate_6_4.png) <!-- .element: class="image-large" -->
--
# LazyLoading : Exemple

![](./img/diapo_formation_hibernate_6_5.png) <!-- .element: class="image-large" -->


--
# LazyLoading

*  On récupère un objet\, et on parcours librement les associations 
*  Très confortable… mais quelques risques à surveiller de près : 
    *  La fameuse <span style="color: red">`LazyInitializationException `</span> !!!
    *  Les risques en termes de performance 

![](./img/diapo_formation_hibernate_6.png)

--
# Le cycle de vie des Entités
![](./img/diapo_formation_hibernate_6_6.png) <!-- .element: class="image-large" -->

--
# Le cycle de vie des Entités
![](./img/diapo_formation_hibernate_6_7.png) <!-- .element: class="image-large" -->

--
# Le cycle de vie des Entités
![](./img/diapo_formation_hibernate_6_8.png) <!-- .element: class="image-large" -->

--
# Le cycle de vie des Entités
![](./img/diapo_formation_hibernate_6_9.png) <!-- .element: class="image-large" -->

--
# Le cycle de vie des Entités
![](./img/diapo_formation_hibernate_6_10.png) <!-- .element: class="image-large" -->

--
# Le cycle de vie des Entités
![](./img/diapo_formation_hibernate_6_11.png) <!-- .element: class="image-large" -->

--
# Le cycle de vie des Entités
![](./img/diapo_formation_hibernate_6_12.png) <!-- .element: class="image-large" -->

--
# Le cycle de vie des Entités
![](./img/diapo_formation_hibernate_6_13.png) <!-- .element: class="image-large" -->

--
# Le cycle de vie des Entités
![](./img/diapo_formation_hibernate_6_14.png) <!-- .element: class="image-large" -->

--
# Le cycle de vie des Entités
![](./img/diapo_formation_hibernate_6_15.png) <!-- .element: class="image-large" -->

--
# Le cycle de vie des Entités
![](./img/diapo_formation_hibernate_6_16.png) <!-- .element: class="image-large" -->

--
# Le cycle de vie des Entités
![](./img/diapo_formation_hibernate_6_17.png) <!-- .element: class="image-large" -->

--
# Le cycle de vie des Entités
![](./img/diapo_formation_hibernate_6_18.png) <!-- .element: class="image-large" -->

--
# Le cycle de vie des Entités
![](./img/diapo_formation_hibernate_6_19.png) <!-- .element: class="image-large" -->

--
# Le cycle de vie des Entités
![](./img/diapo_formation_hibernate_6_20.png) <!-- .element: class="image-large" -->

--
# LazyInitializationException

 * Le LazyLoading ne peut avoir lieu qu'avec les objets en état : MANAGED (ie « géré ») 

 * Sinon →  <span style="color: red">LazyInitializationException   : failed to lazily initialize…</span>

 * Si la transaction a été fermée\, on ne peut plus faire de LazyLoading 

 * →     Nécessité d’« encadrer » le traitement au sein d’une transaction 

--
# LazyInitializationException

 * On peut réattacher un objet au sein d'une autre transaction avec entityManager\.merge() mais… c’est rare d’avoir réellement besoin de plusieurs transactions 

 * Déterminer le bon niveau pour ouvrir la transaction (en général dans la couche service) 

 * Pattern OpenEntityManagerInView (vu plus loin) 

--
# Logique des traitements classiques

 * Ouverture de la transaction 

 * Récupération des objets 

 * Traitements sur les objets 

 * (Optionnel : Détachement des objets qu'on ne veut pas modifier en base)

 * Fermeture de la transaction 

 * Flush des opérations vers la BDD (automatique) 

--
# Inconvénients du LazyLoading

 * Très confortable mais… les requêtes sont générées au coup\-par\-coup 

 * Pas d’anticipation sur les données qui sont nécessaires → Multiplication du nombre de requêtes 

 * Peut conduire à des problèmes de performances (problème du SELECT N\+1) 

--
# Inconvénients du LazyLoading

 * Gare à l'effet surprise !

 * -> Ceux\-ci n’apparaîtront souvent pas dans les tests unitaires ou sur des jeux en volume réduit \! 

 * Conclusion : À utiliser avec parcimonie\, lorsque les volumes ne constituent pas un problème 

--
# TP 6 : Réaliser un traitement
# avec le LazyLoading (1/2)

 * Objectif : Méthode qui calcule les Indices mensuels et annuels d’un secteur 

 * Valeur d’un Indice = somme des montants déclarés par les entreprises à la date de l’indice 

 * Compléter la méthode calculerIndiceSecteur de     SecteurServicesImpl

 ![](./img/diapo_formation_hibernate_7.png)

--
# TP 6 : Réaliser un traitement
# avec le LazyLoading (2/2)

 * Pour les conversion Date → YearMonth et Date → Year se référer aux Converter du TP4 

 * Tester avec SecteurServicesTest 

 * Observer avec le debugger les requêtes générées 

![](./img/diapo_formation_hibernate_7.png)
