# Les bases de Kotlin

## la syntaxe

La syntaxe est très inspirée de Java, avec toutefois quelques particularités dont certaines seront vues ultérieurement

- fichiers `.kt`
- pas de `;`
- déclaration de variables avec `val` / `var`
- typage *postfixé*
- déclaration des fonctions avec `fun`
- les fonctions sont des **entités de première classe**, au même titre que les classes
- pas de `new` pour appeler un constructeur
- les expressions de contrôle varient un peu
- contrôle natif des types nullables
- wildcards de généricité (`extends` ou `super` en Java) avec `in` et `out`
- inférence de type
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

Kotlin fait de l'**inférence de type**, c'est-à-dire qu'il sait déduire le type. Mais une fois défini,
le type reste figé !  

```kotlin runnable
fun main(args:Array<String>) {
    var a = 2
    a = "message"  // Erreur : type mismatch: inferred type is String but Int was expected
}
```

Le mot clé `val` permet de déclarer une constante (mot clé `final` de Java).

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

Les variables et fonctions peuvent être nommées de manière plus flexible grâce à ````. Utile pour nommer les tests unitaires,
ou contourner les conflits de nommage (` mockito.``when``(...)`)

```kotlin runnable
fun  main(args:Array<String>) {
    val `réponse à la vie, l'univers et le reste` = 42
    println(`réponse à la vie, l'univers et le reste`)
}
```

## Cast et Smartcast

Nous pouvons forcer le type d'une valeur avec l'opérateur `as` (unsafe cast operator) ou l'opérateur `as?` (safe cast operator).

```kotlin runnable
fun main(vararg args:String) {
    val value:Any = "CONTENT"
    println((value as String).length)
    println((value as? Int)?.plus(8))
}
```

`is` permet de tester le type d'une variable (cf. `instanceof` de Java). Intervient alors le **SmartCast** dans un bloc `if` ou `when` par exemple !

```kotlin runnable
fun main(vararg args:String){
    val s = "MESSAGE"
    if(s is String){
        println(s.length) // smartcast : pas de (s as String).length
    }
}
```

## Égalité

L'opérateur `==` (double-égale) prend le sens du `.equals()` de Java.

L'opérateur `===` (triple-égale) prend le sens du `==` de Java, donc égalité sur la référence.

## Les chaînes de caractères

Les chaines Multiligne peuvent être écrites sur plusieurs lignes.

Exemple:
``` kotlin runnable
fun  main(args:Array<String>) {
    val haiku = """Un vieil étang
Une grenouille qui plonge,
Le bruit de l'eau."""
    
    val original = """furu ike ya(古池や)
        |kawazu tobikomu (蛙飛込む)
        |mizu no oto (水の音)""".trimMargin()
    
    println("haiku :\n$haiku")
    println("original :\n$original")
} 
```

## Null safety

Kotlin sécurise l'exécution du code avec des types nullable, obligeant le développeur à prendre en compte les cas de nullité.

Exemple : 

```kotlin
var nullable: String? = null
var nonNullable: String = ""

var nonNullable: String = null // erreur de compilation
```

Les types non-nullable, par définition, ne pourront pas être **null**, finis les `if(item == null){…}` !

Pour les types nullable, un arsenal d'opérateurs permettent de ne pas casser le flux de lecture du code.

- `?.` safe call : accès à une propriété / méthode si l'objet n'est pas null, retourne null sinon
- `?:` operateur elvis : retourner une valeur par défaut si null
- `!!` forcer le cast vers un type non-null. `NullPointerException` si l'objet est effectivement null !

```kotlin runnable
fun main(args:Array<String>) {
    val value:String? = null
    println(value?.toUpperCase() ?: "INCONNU")
    println(value!!.toUpperCase()) // NPE !
}
```

Sous le capot, les types nullables ne sont pas *wrappés* avec des classes `Optional`, mais annotés par `@Nullable` et `@NotNull`,
ce qui améliore les performances. 

## les structures de contrôle

### if

Même si le `if` Kotlin est très similaire au `if` java, il peut être utilisé en opérateur ternaire car il peut retourner une valeur.

```kotlin runnable
fun  main(args:Array<String>) {
    val value = 42
    val comparison = if(value > 10) "plus grand que 10" else "moins grand 10"
    println("$value est $comparison")
}

```

### for

L'instruction `for` itère sur tous les types d'objets itérables. Par exemple sur des *ranges* :

```kotlin runnable
fun  main(args:Array<String>) {
    for(i in 0 until 10){
        println(i)
    }
}

```

### when

L'instruction `when` est un *super switch* Java, supportant des conditions complexes.

```kotlin runnable
fun  main(args:Array<String>) {
    val faces = 10
    println(when(faces){
        3 -> "triangle"
        4 -> "quadrilatère"
        5 -> "pentagone"
        6 -> "hexagone"
        7 -> "heptagone"
        8 -> "octogone"
        else -> throw IllegalStateException("polygône non supporté")
    })
}
```

```kotlin runnable
fun  main(args:Array<String>) {
    val temperature = 18
    println(when(temperature){
        in -10..17 -> "trop froid"
        in 18..25 -> "parfait"
        in 26..65 -> "trop chaud"
        else -> throw IllegalStateException("temperature invalide")
    })
}
```

### Exceptions

Toutes les exceptions en kotlin sont *unchecked* (cf. `RuntimeException`), c'est à dire qu'il n'est pas nécessaire d'encadrer systématiquement par des `try`/`catch`/`Finally`
les méthodes qui emettent des exceptions.

Comme souvent en Kotlin, `try` peut retourner une valeur :

```kotlin runnable
fun main(args:Array<String>){
    val input = "dix-huit"
    val number = try{ input.toInt() } catch(e:NumberFormatException){ 0 }
    println(number)
}
```

Le type `Nothing` est le type particulier d'une fonction qui ne retournera jamais rien, une fonction qui part en échec systématiquement.

```kotlin
fun fail(): Nothing {
    throw Exception("Failure")
}
```

Cependant, pour l'interoperabilité avec Java, nous pouvons indiquer que notre fonction envoi une exception avec l'annotation `@Throws`. Le code Java
appelant devra alors l'inclure dans un `try`/`catch`.

```kotlin
@Throws(NumberFormatException::class)
fun parse(str:String): Int = str.toInt()
```

## Fonctions

Les fonctions et méthodes sont déclarées sous la forme `fun name(arg1:T, arg2:U):V { ... }`. 

On peut spécifier des **paramètres par défaut** très simplement, par exemple `fun getWeather(date:LocalDate=LocalDate.now()){}`. 
Cette fonction pourra être appelée avec `getWeather()`

À l'appel d'une fonction, **les paramètres peuvent être nommés**, sans se soucier de l'ordre de déclaration et améliorant la lisibilité.

```kotlin
fun doSomething(one:String, two:Int, three: Int = 0) {
    println("one=$one, two=$two, three=$three")
}
fun main(args:Array<String>){
    doSomething(two=2, one="ONE")
}
```

Une fonction dont le corps ne possède qu'une seule instruction (ou chaîne d'instructions), peut être écrite en **expression body** :

```kotlin
fun square(a:String) = a * a
```

Comme en Java, nous pouvons spécifier des **arguments variables** avec le mot clé `vararg`. Pour passer un tableau ou une collection
à un argument variable, il suffit de le **déstructurer** avec `*`

```kotlin runnable
fun join(vararg values: Int) = values.joinToString(",")

fun main(vararg args: String) {
    val values = intArrayOf(1, 3, 5, 8)
    println(join(*values))
}
```

## Lambdas

Les **lambda expressions** sont déclarées sous la forme `val name: (T, U) -> V = { arg1, arg2 -> ... }`.

Dans une lambda avec un paramètre unique, celui peut alors être omis et se nomme `it`. On n'utilise pas le mot clé `return` pour la valeur de retour d'une lambda.

Si le dernier paramètre d'une fonction est une lambda expression, elle peut être sortie de la liste de paramètres de l'appel.

```kotlin runnable
fun main(args:Array<String>){
    println((0..10).map { it * 2 })
    // est équivalent à :
    println((0..10).map({ value -> value * 2 }))
}
```


# Quizz

## Question 1

```kotlin
var a = 3
a = "Hello"
println(a) 
```

?[Quel est le contenu de a]
-[ ] 3
-[ ] Hello
-[X] Une erreur de compilation

## Question 2

```kotlin
val value = 42
val regex = """\d\W$value"""
println(regex) 
```

?[Quel est le contenu de regex]
-[X] \d\W42
-[ ] \d\W$value
-[ ] Erreur de compilation : Illegal escape: '\d' Illegal escape: '\W'


## Question 3

```kotlin
var message:String? = "Message"
var length = message?.length
```

?[Quel est le type de length ?]
-[ ] Any
-[ ] Int
-[X] Int?
-[ ] Erreur de compilation

## Question 4

```kotlin
fun upper(str:String? = "default") = str.toUpperCase()
```

?[Quel est le résultat de upper()]
-[ ] DEFAULT
-[ ] null
-[ ] NULL
-[X] Erreur de compilation

## Question 5
```kotlin
// Fichier monpackage.MaFonction.kt
fun hello() = println("Hello")
```

?[Depuis Java, comment appelle t-on la fonction hello() ?]
-[ ] monpackage.hello()
-[ ] monpackage.MaFonction.hello()
-[X] monpackage.MaFonctionKt.hello()
-[ ] monpackage.MaFonctionKt.Companion.hello()

## Question 6

```kotlin
val a = "2048"
val b = 2048.toString()
println("${a == b} ${a === b}")
```

?[Qu'affiche ce code ?]
-[ ] false false
-[ ] false true
-[X] true false
-[ ] true true
-[ ] Erreur de compilation

## Question 7

```kotlin
val value = "Message"
println(value as? Int)
println(value as Int?)
```

?[Qu'affiche ce code ?]
- [ ] Message null
- [ ] null null
- [ ] ClassCastException
- [X] null ClassCastException
- [ ] Erreur de compilation

# Exercices
 
## FizzBuzz

Afficher les nombres de 1 à 100, mais afficher **Fizz** si le nombre est divisible par 3, **Buzz** s'il est divisible par 5 et **FizzBuzz** 
s'il est divisible à la fois par 3 et par 5. 


```kotlin runnable
fun  main(args:Array<String>) {
    TODO("FizzBuzz implementation")
}
```