# Les collections Kotlin

On retrouve les grandes catégories de collections en Kotlin : `List`, `Set` et `Map`.

Dans un soucis de performance, Kotlin distingue les collections **mutables** et **immutables**.

## Création

La librairie standard possède les fonctions `listOf(…)`, `setOf(…)` et `mapOf(…)` pour créer des collections immutables,
ou `mutableListOf(…)`, `mutableSetOf(…)` et `mutableMapOf(…)` pour créer des collections mutables.

On trouve aussi quelques variantes comme `hashSetOf(…)`, `hashMapOf(…)`, `linkedSetOf(…)` afin de choisir l'implémentation de sa collection,
ou encore `listOfNotNull(…)` pour assurer que tous les éléments sont non-null.

```kotlin runnable
fun main(vararg args:String){
    val superHeros = listOf("Wonder Woman", "Captain America", "Iron Man", "Doctor Strange")
    println(superHeros)


    val superNames = mutableMapOf(
            "Wonder Woman" to "Diana Prince",
            "Captain America" to "Steve Rogers",
            "Iron Man" to "Tony Stark")
    superNames.put("Doctor Strange", "Stephen Strange")
    println(superNames)
}
```

## Programmation fonctionnelle avec les collections

Java avait introduit la programmation fonctionnelle avec les `Streams`, permettant d'enchainer les transformations d'une collections.

Kotlin va plus loin dans la programmation fonctionnelle et l'enchainement des transformations, directement et sans utiliser les **Streams**.
Si besoin, les **Streams** restent toutfois disponibles en Kotlin via `Collection#stream()` (ajouter la dépendance `kotlin-stdlib-jdk8`).

```kotlin runnable
data class Hero(val name: String, val realName: String)

fun main(vararg args: String) {

    val introduce = { hero: Hero ->
        println("Hi, I'm ${hero.name}")
    }

    mapOf("Wonder Woman" to "Diana Prince",
            "Captain America" to "Steve Rogers",
            "Iron Man" to "Tony Stark",
            "Doctor Strange" to "Stephen Strange")
            .filterKeys { it.contains("man", ignoreCase = true) }
            .map { (name, realName) -> Hero(name, realName) }
            .also(::println)
            .forEach(introduce)
}
```

