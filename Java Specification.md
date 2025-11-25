# Java Specification

## Qu’est-ce qu’une Specification ?

Une *Specification* est une manière de construire des requêtes dynamiques et réutilisables en Java avec JPA, souvent utilisée avec **Spring Data JPA**.  
Elle permet de filtrer les résultats selon plusieurs critères, sans écrire de SQL ou JPQL manuellement.

---

## Pourquoi utiliser les Specifications ?

- Pour créer des filtres complexes (plusieurs champs, conditions, jointures).  
- Pour composer facilement plusieurs critères (**AND**, **OR**, **NOT**) selon les besoins.

---

## Exemple simple de Specification

Supposons une entité `Person` avec un champ `name`.  
On veut filtrer les personnes dont le nom est `"Alice"` :

```java
public class PersonSpecifications {
    public static Specification<Person> hasName(String name) {
        return (root, query, cb) -> cb.equal(root.get("name"), name);
    }
}
```

### Utilisation dans un repository

```java
personRepository.findAll(PersonSpecifications.hasName("Alice"));
```

### Explication ligne par ligne

```java
public static Specification<Person> hasName(String name) {
```

- Méthode statique retournant une `Specification<Person>`.  
- `name` est la valeur à filtrer.

```java
return (root, query, cb) -> cb.equal(root.get("name"), name);
```

- Retourne une lambda implémentant `Specification`.
- `root` : racine de l’entité (accès aux champs).  
- `query` : objet Criteria Query.  
- `cb` : `CriteriaBuilder`, utilisé pour créer les conditions.  
- `cb.equal(root.get("name"), name)` : crée une condition *name = valeur*.

ℹ️ `%` est un joker en SQL/JPA, signifiant « n’importe quelle suite de caractères ».

---

# Fonctions utiles du CriteriaBuilder

Les fonctions principales utilisées dans les Specifications :

| Expression | Description |
|-----------|-------------|
| `cb.equal(root.get("champ"), valeur)` | Égalité |
| `cb.notEqual(root.get("champ"), valeur)` | Différent |
| `cb.like(root.get("champ"), motif)` | Correspondance partielle (`%`, `_`) |
| `cb.notLike(root.get("champ"), motif)` | Négation |
| `cb.greaterThan(root.get("champ"), valeur)` | Strictement supérieur |
| `cb.greaterThanOrEqualTo(root.get("champ"), valeur)` | Supérieur ou égal |
| `cb.lessThan(root.get("champ"), valeur)` | Strictement inférieur |
| `cb.lessThanOrEqualTo(root.get("champ"), valeur)` | Inférieur ou égal |
| `cb.between(root.get("champ"), min, max)` | Entre deux valeurs |
| `root.get("champ").in(valeurs)` | Clause `IN` |
| `cb.isNull(root.get("champ"))` | Champ nul |
| `cb.isNotNull(root.get("champ"))` | Champ non nul |
| `cb.and(pred1, pred2, ...)` | Combinaison AND |
| `cb.or(pred1, pred2, ...)` | Combinaison OR |
| `cb.not(predicat)` | Négation |

---

# Exemple d'utilisation dans un Repository

```java
public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {}
```

### Explication

L’ajout de `JpaSpecificationExecutor<Person>` permet d'utiliser des Specifications avec le repository.

Cette interface ajoute, entre autres :

- `findAll(Specification<T> spec)`
- `count(Specification<T> spec)`

Elle permet donc de créer des requêtes dynamiques **sans SQL ni JPQL**, en utilisant uniquement des Specifications.

---

# Spécifications réutilisables et modulaires

Supposons deux Specifications :

```java
Specification<Person> hasName(String name);
Specification<Person> hasMinAge(int age);
```

### Combinaison AND

```java
Specification<Person> spec = Specification
    .where(hasName("Alice"))
    .and(hasMinAge(18));
```

### Combinaison OR

```java
Specification<Person> spec = Specification
    .where(hasName("Alice"))
    .or(hasName("Bob"));
```

### Utilisation de NOT

```java
Specification<Person> spec = Specification.not(hasName("Charlie"));
```

### Méthode combinée

```java
public static Specification<Person> hasNameOr(String name1, String name2) {
    return Specification.where(hasName(name1)).or(hasName(name2));
}
```

---

# Filtrage sur les relations et jointures

## Exemple 1 : Filtre sur une relation

```java
public static Specification<Person> hasCity(String city) {
    return (root, query, cb) -> cb.equal(root.join("address").get("city"), city);
}
```

Ici, `join("address")` permet d'accéder au champ `city` de la relation.

---

## Exemple 2 : Filtre sur une plage de dates

```java
public static Specification<Person> bornBetween(LocalDate start, LocalDate end) {
    return (root, query, cb) -> cb.between(root.get("birthDate"), start, end);
}
```

---

## Utilisation de `fetch` pour éviter le N+1

```java
return (root, query, cb) -> {
    root.fetch("address", JoinType.LEFT);
    return cb.conjunction();
};
```

### Explication

1. **`root.fetch("address", JoinType.LEFT)`**  
   - Fait un *fetch join* pour charger `address` en même temps que `Person`.  
   - Évite le problème du **N+1**.  
   - `LEFT JOIN` garantit que les personnes sans adresse sont incluses.

2. **`return cb.conjunction();`**  
   - Retourne une condition toujours vraie (`WHERE 1=1`).  
   - La Specification sert uniquement à optimiser le chargement.

### Exemple avec filtre

```java
return (root, query, cb) ->
    cb.equal(root.join("address").get("city"), "Paris");
```

---

# Tests des Specifications

```java
@Test
public void testHasNameSpecification() {
    Specification<Person> spec = PersonSpecifications.hasName("Alice");
    List<Person> result = personRepository.findAll(spec);
    assertTrue(result.stream().allMatch(p -> "Alice".equals(p.getName())));
}
```
