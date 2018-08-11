# Les bases de Kotlin

## la syntaxe

La syntaxe est très inspirée de Java, avec toutefois quelques particularités dont certaines seront vues ultérieurement

- fichiers `.kt`
- pas de `;`
- déclaration de variables avec `val` / `var`
- déclaration des fonctions avec `fun`
- les fonctions sont des **entités de première classe**
- pas de `new` pour appeler un constructeur
- les expressions de contrôle varient un peu
- contrôle natif des types nullables
- wildcards de généricité (`extends` ou `super` en Java) avec `in` et `out`
- …

## variables et typage

On déclare un variable avec `var`. 

```kotlin runnable
fun main(args:Array<String>) {
    var a:Int = 3
    var b:Int = 5
    
    println("a=$a b=$b")
}
```

Kotlin fait de l'inférence de type, c'est-à-dire qu'il sait déduire le type. Mais une fois défini,
le type reste fixé !  

```kotlin runnable
fun main(args:Array<String>) {
    var a = 2
    a = "message"  // Erreur : type mismatch: inferred type is String but Int was expected
}
```

Le mot clé val permet de déclarer une constante (mot clé `final` de Java).

```kotlin runnable
fun main(args:Array<String>) {
    val a = 3
    a = 4 // Erreur : val cannot be reassigned
}

```

Tout est objet, même les types primitifs (pas boxing / unboxing) : `Boolean`,`Byte`,`Int`, `Long`, `String`, `Float`, `Double`, `Unit`

Le type tableau s'écrit `Array<T>` avec `T` le type des éléments du tableau.

```kotlin runnable
fun  main(args:Array<String>) {
    val values:Array<Int> = arrayOf(1,2,3,5,8)
    println(values)
}
```

Le type `Unit` peut-être assimilé au type `Void` de java. Un fonction retourne `Unit` par défaut !
Pour des raison de cohérence dans l'aspect *fonctionnel* du language (toute fonction retourne quelque chose),
 une fonction qui ne retourne rien en théorie, retourne l'**object** `Unit`. `Unit` ne peut prendre en effet qu'une valeur unique, en comparaison `Boolean` en prend 2 : 
 `true` et `false`.
 
```kotlin runnable
fun display(value:String):Unit /* préciser le type Unit est superflu */ { 
    println(value)
}

fun  main(args:Array<String>) {
    display("Hello Kotlin")
}
```

Le type `Any` correspond à n'importe quel type, similaire au type `Objet` de java.

```kotlin runnable
fun display(value:Any) { 
    println(value)
}

fun  main(args:Array<String>) {
    display("Valeur String")
    display(true)
    display(42)
}
```