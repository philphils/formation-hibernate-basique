# Stratégie de 

# chargement complexe 

--
# Lazy attitude

 * On sait donc maintenant mapper les classes et leurs liens (héritage association) 

 * Récupérer/modifier/créer/supprimer les entités 

 * Et maintenant parcourir notre graphe d’objets avec une méthode très simple 

 * …     qui a quelques inconvénients dans certains contextes 

--
# Lazy mais pas trop

 * Le LazyLoading c'est beau mais… 

 * Multiplication du nombre de requêtes 

 * Accès à la base trop nombreux → Source très fréquente de mauvaises performances 

 * Mieux vaut 1 « grosse » requête qui charge tout\, et faire les traitements ensuite 

 * Typiquement le cas pour des batchs sur un volume important 

--
# Problème du N + 1 Selects

![](./img/diapo_formation_hibernate_7_1.png) <!-- .element: class="image-large" -->

 * N \+ 1 requêtes...
 * Et un nombre exponentiel à chaque nouvelle association parcourue !

--
# Problème du N + 1 Selects

 * On pourrait utiliser une jointure 

 * Gain de performance proportionnel au nombre d'entreprises 

```sql
SELECT * FROM ENTREPRISE
JOIN DECLARATION
WHERE … 
```

--
# Stratégie de chargement

*  On cherche donc à charger plusieurs objets du graphe d'un coup 
*  On veut aussi contrôler les objets qui sont chargés 
*  Peut se configurer au niveau du mapping 
*  Peut se réaliser avec des API : 
    *  JPQL : Synthaxe proche du SQL permettant d'écrire des requêtes complexes 
    *  Criteria : Réalisation de requêtes via la constitution d'objet Java 

--
# Stratégie de chargement : Mapping

*  Pour toutes les associations @\*\*\*\*To\*\*\*\* on peut définir un attribut fetch : 
    * `fetch=FetchType.EAGER` : L'objet (ou la Collection) lié sera systématiquement chargé avec l'objet portant l'association 
    * `fetch=FetchType.LAZY` :     L'objet (ou la Collection) lié ne sera pas chargé avec l'objet portant l'association\, mais à la demande (LazyLoading\, JPQL…) 

--
# Stratégie de chargement : Mapping

*  Par défaut, toutes les associations `@***ToOne` ont un `FetchType = EAGER` (la faute à la norme JPA) 
*  Par défaut, toutes les associations `@***ToMany` ont un `FetchType = LAZY` 
--

# Conseil sécuritaire !

 * Conseil :     Mettez toutes vos associations en FetchType = LAZY \!\!\! 
 * Cause très fréquente de dégradations des performances 
 * Mieux vaut contrôler le chargement des associations 
 * En cas de nécessité absolue de `FetchType.EAGER`, possibilité de configuration avec `@Fetch`