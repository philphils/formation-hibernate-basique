# Mettre en oeuvre JPA 
# avec l'implémentation Hibernate

<div style="position: relative;font-size: 0.8em; color: #444; font-style: italic">
 <p class="fragment">
    Persistez, persistez, il en restera toujours quelque chose...
 </p>
 <p class="fragment">
    <br/> (vous avez encore quelques secondes pour fuir…)  
    <br/><img src="./img/diapo_formation_hibernate_0.png" />
 </p>
</div>

--

- Tour de parole pour mesurer le niveau d'expérience
- Questions organisationnelles


--
# Plan

 * Mapper les attributs simples 

 * Réaliser les opérations CRUD 

 * Mapper l’héritage 

 * Mapper les associations 

--

 # Plan

 * Utiliser le LazyLoading 

 * Réaliser des requêtes de chargement complexe avec le JPQL 

 * Réaliser des requêtes de chargement complexe avec Criteria 

 * Configurer JPA/Hibernate 


--
# Introduction



*  Gérer le lien entre les données et les objets est une tâche délicate \! 
*  Complexité technique 
    *  Gestion des connexions 
    *  Fuites mémoires 
    *  Passage d'un modèle à l'autre 
    *  Gestion des clefs étrangères… 
*  Coût important 
--

# Avant les ORMs

 * Mapping « à la main » des objets « BDD » vers les objets « Java » 

 * Risque d'erreur important 

 * Difficulté à parcourir le graphe 

 * Coût important de maintenance en cas d'évolution du modèle 

 * Travail « ingrat » 

--
# Exemple

```java [1-3|4-8|9-17|18-32]
public Medecin chargeMedecin(long medecinId, Connection cnx) {
  if (0 == medecinId || cnx == null) {      return null;       }   
  PreparedStatement stmt = null;
  try {
    // Constitution du PrepareStatement
    stmt = cnx.prepareStatement("SELECT ID, NOM, TELEPHONE FROM MEDECIN WHERE ID = ?");
    stmt.setLong(1, customerId);
    ResultSet rs = stmt.executeQuery();
    // Parcours des résultats et création des objets Java
    if (rs.next()) {
       Medecin medecin = new Medecin();
       int index = 1;
       medecin.setId(rs.getLong(index++));
       medecin.setNom(rs.getString(index++));
       medecin.setTelephone(rs.getString(index++));
       return medecin;
    }
  // Récupération des exceptions
  // et fermeture du PrepareStatement
  } catch (final Exception e) {
       log.info("", e);
  } finally {
       if (stmt != null) {
       	try {
            stmt.close();
           } catch (final SQLException e) {
                  log.info("", e);
           }
       }
  }
      return null;
  }
```
--

# Équivalent avec JPA/Hibernate

```java
public Medecin chargeMedecin(long medecinId) {
  TypedQuery<Medecin> query = entityManager.createQuery(« SELECT med FROM Medecin med where id =:id »);
  query.setParameter(« id », medecinId) ;
  return query.getSingleResult(); 
}
```
* <!-- .element: class="fragment" --> Et voilà !...

--

# Ou encore plus simple : 

```java
public Medecin chargeMedecin(long medecinId) {
   return entityManager.findById(medecinId, Medecin.class); 
}
```
--
# Et encore plus simple
# avec Spring-Data

```java
public interface MedecinRepository extends JpaRepository<Medecin, Integer>{}
```

 * <!-- .element: class="fragment" --> Permet de récupérer 1 ou tous les médecins par leur identifiant, de les supprimer, de les mettre à jour etc...

--
# Naissance d'Hibernate

 * Gaving King\, développeur principal\, début 2001 

 * Produire un outil qui prenne en charge le lien entre BDD et objet Java 

 * Concentration sur le modèle objet Java 

 * Lien « organique » entre modèle Java et modèle BDD 

--
# Naissance de la norme JPA

 * Java Persistence API\, version 1\.0 en 2006 

 * Permet de limiter la dépendance à une implémentation 

 * Hibernate reste de loin l'implémentation la plus utilisée\, nombreuses fonctionnalités supplémentaires 

 * Aujourd'hui JPA version 2\.1\, Hibernate version 5\.9 pour la version XML 

 * Hibernate 6\.1 pour la version Spring\-Boot 3 

--
# Critique envers les ORMs

 * Certains développeurs critiquent la trop grande complexité d'Hibernate 

 * Aspect « boîte noire » 

 * Caractère « Statefull » 

 * Nécessité de comprendre la mécanique 

 * …     Mais comment c'était avant ??\! 

--
# TP 1 : Avant les ORMs

![](./img/diapo_formation_hibernate_1.png)

*  Créer un schéma test 
*  Restaurer le jeu de données fourni : 
    *  Executer le script dans     src/test/resources/tp1 


--

# TP 1 : Avant les ORMs

*  Créer la méthode de récupération de toutes les entreprises triées par date de création dans EntrepriseDAOJDBCImpl 
*  Tester avec EntrepriseDAOJDBCTest 
*  En cas de problème d’exécution avec la version de Junit\, faire « Run as → Run Configuration » et sélectionner la bonne version (Junit 5 pour version Spring\-Boot 3) 

--

# Conclusion

 * Retour arrière difficilement envisageable 

 * Considérer Hibernate/JPA d’emblée comme un framework « lourd » 

 * Coût d'entrée important\, mais accepter de l'assumer 
