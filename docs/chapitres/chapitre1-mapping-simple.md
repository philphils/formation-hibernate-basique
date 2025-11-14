# Mapping des  

# attributs simples 

--
# Le mapping des attributs simples

 * On va d’abord voir comment mapper un objet avec des attributs simples 

 * Pas de liens entre les objets pour l’instant 

 * Correspond à une seule table en BDD\, sans clefs étrangères 

--
# Principes de cette formation



*  Versions utilisées pour la formation (avec et sans Spring Boot) : 
    *  Hibernate 5\.9 ou 6\.1 
    *  Spring 4\.3 ou 6\.0 
*  Les partie « Conseil : » sont personnels\, certains développeurs sûrement d’avis différents 
*  Tentative de montrer aussi « Comment utiliser Hibernate »\, car framework riche et parfois on s’y perd 
--
# Les principes d'Hibernate/JPA

 * Se concentrer sur le modèle objet Java 

 * Ajout de métadonnées sur ce modèle 

 * Lien « organique » entre modèle Java et modèle relationnel 

 * Génération du schéma de la base → la structure de la BDD « découle » des objets Java \+ config d’Hibernate 

--
# XML vs Annotations

 * À l'origine\, le « mapping » est réalisé avec des fichiers XML 

 * Depuis évolution vers un mapping via les annotations 

 * Mapping directement intégré au code des classes métier 

 * Utilisation des annotations meilleure garantie pour la correction des bugs 

--
# Mise en place du mapping

*  On utilise @Entity pour déclarer qu'une classe métier représente une table 
*  La classe doit contenir : 
    *  Un constructeur vide (présent par défaut) 
    *  Un identifiant (expliqué plus loin) 
*  Par défaut nom de table = nom de la classe 

--
# Mise en place du mapping

*  Conseil : Conserver le comportement par défaut autant que possible  
*  Permet d’avoir une cohérence :  
    *  notions métier → classes objets Java → nom des objets BDD 
*  Pour définir un autre nom de table : @Table 

--
# La déclaration des identifiants

*  Une « entité » doit définir un identifiant 
*  Correspond au besoin d’une clef primaire pour une table en BDD 
*  Conseil : Préférer les identifiants techniques (règles métier peuvent évoluer) 
*  Se déclare avec @Id 

--
# La déclaration des identifiants

*  Possibilité de déclarer des identifiants composites (pas conseillé) avec : 
    * `@EmbeddedId` (expliqué plus loin) 
    *  `@IdClass`

--
# La déclaration des attributs/colonnes (1/2)

*  Par défaut : Nom de colonne = nom de l'attribut 
*  Le CamelCase devient → camel\case (underscore) 
*  Tous les types     basiques       sont mappés par défaut vers les types BDD correspondants 

--
# La déclaration des attributs/colonnes (1/2)

*  Exemples : 
    *  `java.lang.String` → VARCHAR 
    *  `java.lang.Boolean` → BOOLEAN 
    *  `java.util.Date` → TIMESTAMP … 
*  Avec `@Column`, possibilité de préciser le nom\, la taille\, des contraintes(par ex\.     unique    \,     non nul    ) \.\.\. 

--
# La déclaration des attributs/colonnes (1/2)

*  Pour les dates\, possibilités de choix du type plus fin avec @Temporal 
*  Les enums doivent être annoté avec @Enumerated avec au choix : 
    *  EnumType\.    STRING     : Les valeurs stockées correspondent au nom des valeurs d'enum (plus parlant) 
    *  EnumType\.    ORDINAL    : Les valeurs stockées correspondent à une numérotation des valeurs de l'énum (problème si ajout de nouvelles valeurs d’enum) 


--
# La déclaration des attributs/colonnes (1/2)

*  Tous les types complexes nécessitent un mapping plus élaboré 
    * Classes "maison"
    * Classes récupérées d'autres librairies
    * etc...
--
# Exemple :

![](./img/diapo_formation_hibernate_1_1.png) <!-- .element: class="image-large" -->

--
# Exemple :

![](./img/diapo_formation_hibernate_1_2.png) <!-- .element: class="image-large" -->


--
# Exemple :

![](./img/diapo_formation_hibernate_1_3.png) <!-- .element: class="image-large" -->


--
# Exemple :

![](./img/diapo_formation_hibernate_1_4.png) <!-- .element: class="image-large" -->


--
# Exemple :

![](./img/diapo_formation_hibernate_1_5.png) <!-- .element: class="image-large" -->


--
# Exemple :

![](./img/diapo_formation_hibernate_1_6.png) <!-- .element: class="image-large" -->



--
# La génération des 
# valeurs d'identifiants

 * Peut\-être gérée en dehors d'Hibernate (déconseillé) → Pas de déclaration 

 * JPA/Hibernate a un effet « aspirant » : Plus on l’utilise\, plus il faut l’utiliser 

 * Préférable de ne pas « mixer » les technologies 

 * La génération de valeurs d’identifiant via Hibernate se déclare avec @GeneratedValue 

--
# La génération des 
# valeurs d'identifiants

 * Par défaut @GeneratedValue en place une séquence en BDD (avec Postgres) 

 * Le nom est déterminé par l’entité 

 * Ex : Medecin → medecin_seq 

 * Increment positionné à 50 par défaut (important cf diapos suivantes) 

 * Convient bien mais attention à la dépendance au nom de l’objet 

--
# Choix d'une « stratégie »

 * 4 possibilités pour générer les identifiants via Hibernate  

 * SEQUENCE : utilisation d'une séquence avec @Sequence Generator 

 * TABLE : utilisation d'une table avec @TableGenerator 

 * IDENTITY : on se repose sur un mécanisme inhérent à la base (type SERIAL pour Postgres) 

 * AUTO : on laisse Hibernate choisir (déconseillé)\, par défaut séquence sous Postgres 

--
# Exemple :
# Utilisation des séquences

```java
@Id
@SequenceGenerator(name = "SEQUENCE_GEN",
     sequenceName="hibernate_sequence",allocationSize = 20) 
@GeneratedValue(strategy = GenerationType.SEQUENCE,
     generator = "SEQUENCE_GEN")
private Integer id;
```

--
# Exemple :
# Utilisation des séquences

 * Importance du paramètre     allocationSize     pour les performances : 

    * →     Met en œuvre un algorythme type « Hilo » pour attribuer plusieurs identifiants avec un seul appel à la séquence 

 * Cf formation « Hibernate Avancée » 

--
# Le cas des UUID

 * Identifiants générés par algorithme qui garantit l'unicité à un epsilon près 

 * Intéressant pour des problématiques de distribution 

 * Mais peut être volumineux en BDD (16 octets au min) 

--
# Le cas des UUID

 * Utilisation d'une annotation spécifique d'hibernate @UuidGenerator : 

```java
@Id
@GeneratedValue
@UuidGenerator 
private UUID uuid;
```

--
# Les attributs Embedded

*  Regroupement de plusieurs colonnes dans un même objet 
*  On regroupe plusieurs attributs qui forment un tout cohérent 
*  Mapping identique aux entités\,     mais sans Id 
*  Conséquence : pas de requête directe possible 
*  Utile pour des objets qui n'ont pas de vie indépendante d'un objet\-conteneur 
--
# Les attributs Embedded

*  Exemple type : l'adresse de l'utilisateur → Notion propre à l'utilisateur 
*  Question à se poser : 
    *  Vais\-je manipuler cet objet indépendamment de son conteneur ? 
    *  Dois\-je récupérer directement l'objet depuis la base sans passer par son conteneur ? 

--

# Les attributs Embedded

*  Se déclare dans l'objet contenant avec @Embedded (imbriqué)
*  L'objet « imbriqué » est lui annoté avec @Embeddable (« imbriquable ») 

--

# Les attributs Embedded

*  Si réutilisation\, surcharge possible du mapping avec @AssociationOverride (modification des noms de colonnes\, des types de stockage etc\.) 
*  Possibilité de définir des Collections d'attributs embedded avec @CollectionElement (on a alors une table sans clef primaire) 
--
# Exemple

![](./img/diapo_formation_hibernate_1_7.png) <!-- .element: class="image-large" -->

--
# Exemple

![](./img/diapo_formation_hibernate_1_8.png) <!-- .element: class="image-large" -->

--
# Exemple

![](./img/diapo_formation_hibernate_1_9.png) <!-- .element: class="image-large" -->


--
# Le mapping des dates

*  Il est fortement conseillé d’utiliser les dates de la nouvel    les API     java\.time 
* On utilisera donc les types (type correspondant en BDD) : 
    *  LocalDate → pour une date sans heure (DATE) 
    *  LocalTime → pour une heure sans date (TIME) 
    *  LocalDateTime → date et heure (TIMESTAMP) 
    *  ZonedDateTime et OffsetDateTime → date et heure avec fuseau horaire (TIMESTAMP WITH TIME ZONE) 
    *  Instant → date UTC     (TIMESTAMP WITH TIME ZONE) 

--
# Le mapping des dates

*  Le mapping vers les types correspondants sen BDD est alors automatique 
*  Dans le cas d’utilisation de l’ancienne API     java\.util\.Date    \, il faut ajouter @Temporal pour préciser le type en BDD 

--
# Les Converter

*  Pour les attributs non\-basiques mais peu complexes\, qui ne correspondent pas à un objet métier (ex : Year\, YearMonth\, ou vos propres classes… ) 
*  Possibilité de les mapper en définissant la façon de les convertir en valeur pour la BDD 

--
# Les Converter

*  On définit une classe annotée @Converter qui implémente AttributeConverter et ses 2 méthodes : 
    *  ConvertToDatabaseColumn 
    *  ConvertToEntityAttribute 
*  Possibilité aussi de définir ou réutiliser des conversions avec @Type 

--
# Les Converter : exemple

```java
@Converter(autoApply = true)
public class YearMonthConverter 
        implements AttributeConverter<YearMonth, LocalDate> {
    @Override
    public LocalDate convertToDatabaseColumn(YearMonth yearMonth) {
        return LocalDate.of(yearMonth.getYear(),
             yearMonth.getMonth(), 1);
    }
    @Override
    public YearMonth convertToEntityAttribute(LocalDate localDate) {
        return YearMonth.from(localDate);
    }
}
```

--
# TP 2 : 
# Mapping des attributs simples

 * Lire le script de création du schéma fourni 

 * Mapper les classes Entreprise\, Declaration\, Secteur pour obtenir un schéma identique 

 * Lancer TestGenerationScript pour générer votre script et vérifier qu'il correspond 

![](./img/diapo_formation_hibernate_2.png)



--
# TP 2 : 
# Mapping des attributs simples

 * Le script est généré à la racine du projet\, nommé     create\.sql 

 * Attention supprimer le script avant de relancer sinon les commandes sql s’accumulent 

 ![](./img/diapo_formation_hibernate_2.png)

