# Configuration 
# d’Hibernate 
# et outils annexes

--
# La configuration de JPA/Hibernate

* La configuration s'est simplifiée avec les nouvelles versions 

* Un seul fichier optionnel : persistence\.xml 

* Normalement dans le répertoire META\-INF 

* Normalement\, on y déclare les infos pour la connexion (url\, username\, password… ) 

* Sauf que\, comme on utilise InseeConfig\, ou Spring\, pour les properties\, on y déclare souvent le strict minimum… ou rien du tout 

--
# La configuration de JPA/Hibernate

* Seule info : le nom de l'unité de persistence 

* Unité de persistence : Objet correspondant conceptuellement à une source de données 

* Plusieurs unités de persistence possibles si plusieurs BDD par ex\. (rare normalement) 

```xml
<persistence-unit name="persistenceUnit">
    <description>
        Persistence unit pour formation Hibernate 5
    </description>
</persistence-unit>
```

--
# Les composants essentiels

*  JPA utilise essentiellement 2 composants : 
    *  `EntityManagerFactory` : 1 EntityManagerFactory est créé par unité de persistence\, au démarrage du serveur (ou du batch) 
    *  `EntityManager` : on l'a déjà utilisé\, est créé par le composant précédent\, en général 1 par transaction 

--
# L' `EntityManagerFactory`

*  L' `EntityManagerFactory` contient les métadonnées sur le mapping des objets 
*  `EntityManagerFactory`, objet de plus haut niveau\, plus long à créer 
* Contient l'ensemble des informations de mapping
--
# L' `EntityManager`

*  On manipule plus souvent directement l'`EntityManager`
*  L’`EntityManager` est créé de manière transparente à l’ouverture d’une transaction 
* Permet d'intéragir avec la BDD

--
# Passage des properties

 * Lorsqu'on utilise Spring\-Boot ou InseeConfig (qui utilise Spring)\, on préfère passer les infos de connexions via Spring 

 * On crée donc souvent l'`EntityManagerFactory` dans les fichiers Spring 

 * Avec Spring\-Boot la création de     l'`EntityManagerFactory` est transparente 

 * On rencontre à l'Insee 2 types de config le plus souvent 

--
# Configuration avec InseeConfig

 * On créé l'`EntityManagerFactory` en donnant en paramètre directement le Pool de connexion créé par InseeConfig (ou SD44Configuration) 

 * Problème : Besoin d'accéder parfois à la DataSource\, ie l'objet auquel est rattaché le pool de connexion 

 * Par exemple pour logger les requêtes     avec les paramètres    \, et pour avoir un compteur de requêtes (cf TP7 et 8), on veut pouvoir accéder à la `DataSource` pour la « wrapper » 

--
# Configuration avec InseeConfig

 * Alternative : Récupérer la DataSource depuis InseeConfig\, et créer l'EntityManagerFactory : 

![](./img/diapo_formation_hibernate_9_1.png) <!-- .element: class="image-large" -->

--
# Configuration avec InseeConfig

 * Alternative : Récupérer la DataSource depuis InseeConfig\, et créer l'EntityManagerFactory : 

![](./img/diapo_formation_hibernate_9_2.png) <!-- .element: class="image-large" -->

--
# Configuration avec InseeConfig

 * … Et créer l'EntityManagerFactory avec la référence à la `Datasource`: 
```xml [1-20|5-6]
<bean id="myEmf" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
    <property name="jpaVendorAdapter">
        <bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter" />
    </property>
    <!-- référence à la Datasource -->
    <property name="dataSource" ref="dataSource" />
    <property name="jpaProperties">
        <props>
            <prop key="hibernate.default_schema">${fr.insee.formation.hibernate5.schema}</prop>
            <!-- generate ddl -->
            <prop key="javax.persistence.schema-generation.database.action">drop-and-create</prop>
            <!-- dialect -->
            <prop key="hibernate.dialect">org.hibernate.dialect.PostgreSQLDialect</prop>
            <prop key="hibernate.connection.useUnicode">true</prop>
            <prop key="hibernate.connection.charSet">UTF-8</prop>
            <!-- logging debug information -->
            <prop key="hibernate.show_sql">true</prop>
            <prop key="hibernate.format_sql">true</prop>
            <!-- … (De nombreuses autres propriétés disponibles, voir dans la doc) -->
        </props>
    </property>
```

--
# Configuration avec Spring-Boot

 * Spring\-Boot permet de disposer d’une configuration JPA/Hibernate avec très peu de code 

 * Il suffit d’ajouter le starter « data-jpa » au pom 

 * On renseigne ensuite la chaîne `spring.datasource.url`

--
# Configuration avec Spring-Boot


 * Spring\-Boot en déduit alors le type de base données et met en place tous les composants nécessaires 

 * Spring\-Boot met en place un pool de connexion « Hikari » plus performant que « DBCP » d’InseeConfig (ou SD44Configuration) 

--
# Récupération des composants JPA

*  On peut ensuite injecter l'`EntityManager` dans une classe avec `@PersistenceContext` ou `@Autowire` (Spring) : 
```java
@PersistenceContext
private EntityManager entityManager;
```

--
# Récupération des composants JPA

*  Attention :     Par défaut celui-ci n'existe que dans lorsqu'une transaction est ouverte !
* Dans le cas contraire : <span style="color: red">`TransactionRequiredException`</span> !
*  De même, la récupération de l'`EntityManagerFactory` se fait avec `@PersistenceUnit` ou `@Autowire` 
--
# Le pattern `GenericDao`

*  Ce pattern est utilisé pour factoriser les méthodes de base de l'`EntityManager`
*  On définit une interface `GenericDao` que tous les DAOs implémentent 
*  Une classe abstraite `AbstractGenericDao` qui récupère l'`EntityManager` et qui lui délègue : 
    *  `persit()`
    *  `merge()`
    *  `remove()`
    *  `find()` …  
--
# Le pattern `GenericDao`

*  Ainsi pour tous les DAOs ces méthodes sont disponibles 
*  <u>Conseil :</u> Ne pas renommer les méthodes de l'`EntityManager` dans l'`AbstractGenericDao` car risque de confusion 
*  Exemple fourni dans les corrections avec `SecteurDao` 
*  Avec Spring\-Data\, c'est ce pattern qui est mis en œuvre avec les repositories 

--
# Logger les requêtes :
# un outil de survie !

*  On l'a vu\, JPA peut conduire à multiplier les requêtes et dégrader les performances 
*  Il faut donc surveiller les requêtes générées
*  Plusieurs properties à dispo : 
```properties
#logger les requêtes
hibernate.show_sql = true 
#formatter le sql
hibernate_.format_sql = true
#avoir des commentaires
hibernate.use_sql_comments = true 
```
--
# Logger les requêtes :
# un outil de survie !

*  Permets de tracer les requêtes générées\. Ex : 

```console
Hibernate:     
select secteur0_.id as id1_5_0_, 
        secteur0_.codeNaf as codeNaf2_5_0_,
        secteur0_.libelleNomenclature as libelleN3_5_0_ 
    from
        test.Secteur secteur0_ 
    where
        secteur0_.id=?
```
* <span style="color: red">Attention :</span> A désactiver absolument en production 

--
# Utiliser un wrapper de DataSource

* La configuration via la `DataSource` permet de mettre en place simplement un wrapper

* Permet d’ajouter des fonctionnalités : ex. logger les requêtes avec les paramètres 

* Peut-être utile pour exécuter simplement les requêtes sur la base et s'assurer de l'origine des problèmes de perfs (nb de requêtes, ou requêtes trop longues) 

* D'autres fonctionnalités intéressantes… 

--
# Utiliser un wrapper de DataSource

*  Il existe plusieurs wrapper de datasource dispo\. Ex : 
    *  P6spy 
    *  Datasource\-proxy 
    *  Log4jdbc… 
*  P6spy permet d'avoir la requête avec les paramètres renseignés 
*  Datasource\-Proxy propose une fonctionnalité supplémentaire très intéressante : Un compteur des requêtes envoyées \!

--
# Utiliser un wrapper de DataSource

*  Ainsi on peut faire un comptage dans le test unitaire pour s'assurer de détecter toute régression en termes de nombres de requêtes 
*  <u>Conseil :</u> Datasource\-proxy semble le plus intéressant (cf config pour le TP7 et 8) pour les comptages 
*  <u>Conseil :</u> P6Spy pour disposer de requêtes prêtes à être exécutées directement en BDD 
--
# Génération des scripts SQL

*  Hibernate permet de générer les scripts de base de données\, ainsi que les màj auto du schéma 
*  En phase de développement\, très utile 
*  En phase de production\, peut\-être une aide\, mais doit être testée (pas de màj directe !) 

--
# Génération des scripts SQL

*  Un outil d'historisation des modifs du schéma est aussi souhaitable en prod (Flyway ou Liquibase) 
*  Paramètre à renseigner : 
     *  `javax.persistence.schema-generation.database.action`     : Type d'action à effectuer (create\, drop\, update\, validate…) 
     *  `javax.persistence.schema-generation.scripts.action`     : Type de scripts à générer (de même create\, drop\, update\, validate…) 

--
# Génération des scripts SQL

*  En test, `drop-and-create` très utile pour nettoyer la base entre les tests
* Lors d'une évolution `update` utile pour récupérer le script de màj de la BDD
*  Voir la doc pour plus d'infos : https://docs.hibernate.org/orm/7.1/userguide/html_single/#settings-jakarta.persistence.schema-generation.database.action

--
# OpenEntityManagerInView

* On a vu qu'on ne pouvait pas utiliser le LazyLoading en dehors d'une transaction 

* Mais parfois\, on voudrait l'utiliser\, simplement pour de l'affichage par ex\.\, et sans faire de modification 

* On doit alors ouvrir une transaction pour simplement lire des données… Pas très cohérent \! 

--
# OpenEntityManagerInView

* Dans le contexte d'une appli web\, ou d'un web\-service\, on peut ouvrir un EntityManager en mode lecture seulement tant qu'aucune transaction n'est déclarée 

* Assez pratique… Mais toujours\, attention aux requêtes générées \!
    → #show_sql=true

--
# OpenEntityManagerInView

* Mise en place : Il faut ajouter un filtre au web.xml
* Spring en propose un :

```xml
  <filter>
    <filter-name>openEntityManagerInViewFilter</filter-name>
    <filter-class>org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter</filter-class>
    <init-param>
      <param-name>entityManagerFactoryBeanName</param-name>
      <param-value>myEmf</param-value> <!-- ici le nom du bean pour l'EntityManagerFactory -->
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>openEntityManagerInViewFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
```
--
# OpenEntityManagerInView

* Son utilisation fait débat 

* Il y a des avantages et des inconvénients selon les contextes 

* Un bon résumé ici : https://www.baeldung.com/spring-open-session-in-view 

* Spring\-Boot le met en place par défaut (désactivable avec la property `spring.jpa.open-in-view=false`)

--
# Spring Data :
# Une surcouche fort sympathique !

*  Module de la galaxie Spring 
*  Se configure simplement en passant l’`EntityManagerFactory` à un bean Spring 
*  Ou avec Spring-Boot de manière transparente 
*  Permet de réduire au minimum (voir de supprimer complètement) le code technique JPA

--
# Spring Data

* En mode Spring (sans Spring-Boot), une dépendance à ajouter :
```xml
<dependencies>
  <dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-jpa</artifactId>
  </dependency>
<dependencies>
```
* Un bean à configurer : <!-- .element: class="fragment" -->
```xml
<jpa:repositories base-package="fr.insee.repository" 
                entityManagerFactoryRef="myEntityManagerFactory" />
```
<!-- .element: class="fragment" -->
--
# Spring Data


Et c’est la fête ! 🥳🥳🥳


--
# Spring Data

* On ne définit que des interfaces :
```java
public interface SecteurRepository 
        extends Repository<Secteur, Integer> {}
```

* L’implémentation est générée par Spring\-Data JPA 

* On dispose de base des méthodes CRUD basiques 

--
# Spring Data

* On peut déclarées des méthodes supplémentaires implémentées par convention de nommage 

* Ex:  
```java
public Secteur findByCodeNaf(String codeNaf);
```

* Génère : <!-- .element: class="fragment" -->
```java
entityManager.createQuery("SELECT ent FROM Entreprise 
        WHERE codeNaf = :codeNaf", Entreprise.class);
entityManager.setParameter("codeNaf", codeNaf);
query.getSingleResult();
```
<!-- .element: class="fragment" -->

--
# `@Query`

* Pour des méthodes plus complexes on utilisera `@Query` directement dans l’interface
* Ex :
```java
@Query("SELECT sect FROM Secteur sect  "
            + "JOIN FETCH secteur.entreprises entreprise "
            + "JOIN FETCH entreprise.declarations declaration "
            + "JOIN FETCH sect.indices indice "
            + "WHERE sect.codeNaf = :codeNaf")
public Secteur findByCodeNafWithEntreprisesAndDeclarationAndIndicesJPQL(
            String codeNaf);
```

--
# `@Query`

* Avantage : Spring vérifie la synthaxe 

* On est donc averti par une exception en cas d’erreur de synthaxe dès le démarrage du serveur\, même sans test \! 


* Qu'il faut faire quand même 😡😁 <!-- .element: class="fragment" -->

--
# JPA, Hibernate de nombreuses
# autres fonctionnalités (1/2)

* Mise en place de verrous sur les données 

* Mise en place d'un cache de données (pour les données de référence) 

* Historisation des données

* Tunning pour l'écriture et la lecture des données 

--
# JPA, Hibernate de nombreuses
# autres fonctionnalités (2/2)

* Utilisation des curseurs de BDD (pour les très gros volumes en batch) 

* Gestion des données spatiales (par ex\. avec PostGis) 

* Et bien d'autres… Mais ce sera pour une autre fois \! (formation Hibernate Avancé) 😎

--
# Merci de votre attention 

# Avez\-vous des questions ?

```
Philippe Sabaa
```
<!-- .element: class="fragment" -->
```
SNDIN
```
<!-- .element: class="fragment" -->
```
INSEE
```
<!-- .element: class="fragment" -->
```
Naoned
```
<!-- .element: class="fragment" -->