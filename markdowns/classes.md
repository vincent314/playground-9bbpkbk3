# Classes et Objets

## Déclaration et instanciation

Les méthodes et attributs sont par défaut **public**. Des getters (`val` / `var`) et setters (`var`) sont
générés pour les attributs.

```kotlin runnable
class User{
    var name:String? = null

    fun introduce() = "Bonjour, mon nom est ${name?.toUpperCase()}"
}

fun main(args:Array<String>) {
    val user = User()
    user.name = "John" // setter
    println(user.name) // getter
    println(user.introduce())
}
```

## Constructeurs

Les paramètres du **constructeur primaire** viennent juste après le nom de la classe. Les paramètres taggés `val` ou `var` seront des attributs de la classe.

L'implémentation du **constructeur primaire**, souvent optionnel, se fait dans un bloc `init { … }` 

```kotlin runnable
class User(val name:String, age:Int){
    init {
        println("Initialisation avec l'attribut name=$name et le paramètre age=$age")
    }
    override fun toString() = "Mon nom est $name" // age est inaccessible ici
}

fun main(args:Array<String>){
    println(User("John Doe", 40))
}
```

Les **constructeurs secondaires** sont définis avec les blocs `constructor() { … }`

```kotlin runnable
class Movie(var name:String) {
    constructor(other: Movie) : this(other.name) {
        println("Secondary constructor with movie.name='$name'")
    }
}
fun main(args:Array<String>){
    Movie(Movie("Back to the Future II"))
}
```

## Accesseurs personnalisés

Si nécessaire, nous pouvons personnaliser les getters et les setters habituellement générés automatiquement. Remarquez la
variable réservée `field` !

```kotlin runnable
class Dog {
    var name: String? = null
        get() {
            println("Getting name field")
            return field?.toUpperCase()
        }
        set(value) {
            println("Setting name field")
            field = value
        }
}

fun main(args:Array<String>){
    val goliath = Dog()
    goliath.name = "goliath"
    println("Le nom du chien est ${goliath.name}")
}
```

## Héritage

**Les classes sont `final` par défaut.** Pour qu'une classe hérite d'une classe parente, cette dernière doit être taggée `open`

```kotlin runnable
open class Mother{
    var name:String = "Mother"
}
class Daughter:Mother(){
    var address:String = ""
}

fun  main(args:Array<String>) {
    val d = Daughter()
    d.name = "Manue"
    d.address = "1, rue Jean Jaurès, Gueret"
    println(d)
}
```

## Data classes

En plus des accesseurs, Kotlin peut générer les méthodes `equals`, `hashcode`, `toString` et `clone`, bien pratiques pour
les classes de modèle ou **data classes**. C'est une fonctionnalité très similaire à l'annotation `@Data` de **Lombok**.

**Attention**: les data classes fonctionnent mal avec l'héritage, 

```kotlin runnable
data class Person( var firstName:String, var lastName:String, var userCode:String)

fun main(vararg args:String){
    val john = Person("John", "Doe", "123456")
    println(john)
    val jane = john.copy(firstName = "Jane", userCode="67890")
    println(jane)

    println(john == Person("John", "Doe", "123456"))
}
```

**Attention** : Les attributs pris en compte dans ces méthodes sont ceux déclarés dans le constructeur par défaut.

```kotlin runnable
data class Person(var firstName: String, var lastName: String) {
    var id: String? = null
}


fun main(vararg args: String) {
    val john = Person("John", "Doe")
            .apply { id = "123456" }
    println(john)
}
```

C'est une manière d'ignorer certains champs (dans le cas de boucle par exemple A <--> B).

## Interface

Comme en Java, les interfaces sont des prototype de classe sans implémentation.

```kotlin
interface IDisplay{
    fun getSize(): Pair<Int,Int>
}

class Display:IDisplay,Serializable{
    override fun getSize(): Pair<Int, Int> {
        TODO("not implemented")
    }
}
```

## Object et Singleton

Kotlin supporte nativement (et thread-safe) la création d'une instance unique d'une classe avec le mot-clé `object`.

```kotlin runnable
object Singleton{
    var value:Int = 42
}

fun main(vararg args:String) {
    println(Singleton.value)
    Singleton.value = 999
    println(Singleton.value)
}
```

On utilise aussi `object` pour créer des classes anonymes.

```kotlin runnable
interface IService {
    fun doSomething()
}

fun main(vararg args: String) {
    val anonymousClazz = object : IService {
        override fun doSomething() {
            println("Hello there !")
        }
    }
    anonymousClazz.doSomething()
}
```

## Fonctions d'extension

Les fonctions d'extensions sont une notion important en Kotlin : elles permettent d'étendre facilement les fonctionnalités 
d'une classe à l'extérieur de celle-ci. 


Au sein de cette **fonction d'extension**, nous avons accès aux attributs membres de la classe en passant par la variable `this`

```kotlin runnable
// { autofold
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
// }

fun Date.toLocalDate(): LocalDate = Instant.ofEpochMilli(this.time).atZone(ZoneId.systemDefault()).toLocalDate()

fun main(vararg args:String){
    println(Date().toLocalDate())
}
``` 

## Companion objects

La notion de `static` en Java est remplacée par la notion de `companion object` en Kotlin. Pour rendre *statiques* des attributs
ou des méthodes, on les encapsule dans un object singleton associé à la classe

```kotlin runnable
class HasStaticMembers {
    companion object {
        fun doSomething(){
            println("Hello companion !")
        }
    }
}

fun main(vararg args:String){
    HasStaticMembers.doSomething()
}
```

Les **companion objects** sont plus intéressants d'un point de vue du design d'application, car ils peuvent étendre et implémenter d'autres classes/interfaces.
On peut aussi associer une fonction d'extension aux **companion objects** :

```kotlin runnable
class A(){
    companion object{
        fun hello() = println("Hello")
    }
}

fun A.Companion.bye() = println("Bye")

fun main(vararg args:String){
    A.hello()
    A.bye()
}
```

Cependant, depuis Java (voir la décompilation de la classe), l'appel devra se faire ainsi `HasStaticMembers.Companion.doSomething()`. 
Pour les utilisateurs Java de votre librairie, ajoutez l'annotation `@JvmStatic` à vos membres `HasStaticMembers.doSomething()` 

**Exercice** : Comparer les deux versions Java générées avec ou sans `@JvmStatic`


## Fonctions infix

Les méthodes ou les fonctions d'extension à **un seul paramètre strictement et taggées** `infix`, peuvent être appelées avec une notation spéciales : sans point ni paranthèses !

```kotlin runnable
class Person(val name:String) {
    var marriedTo:Person? = null

    infix fun marry(to: Person) {
        this.marriedTo = to
        to.marriedTo = this
    }
}

fun main(vararg args:String){
    val john = Person("John")
    val bob = Person("Bob")

    john marry bob
    println("John is married to ${john.marriedTo?.name}")
}
```

De nombreuses fonction d'extension existent dans le language et dans les nombreuses librairies Kotlin. `to` est probablement la plus connue et utilisée.
Elle permet de créer un objet `Pair` (de la bibliothèque Kotlin), un tuple associant 2 valeurs.

```kotlin runnable
fun main(vararg args:String){
    val first: Pair<Int,String> = 1 to "First"
    println( first == Pair(1, "First")) // true
}
```

## Surcharge d'opérateurs et conventions

Avec le mot clé `operator`, nous pouvons surcharger les opérateurs usuels pour les utiliser avec nos propres classes, 
en implémentant les méthodes suivantes :

### opérateurs unaires

- `inc` : `a++`
- `dec` : `a--` 

### opérateurs binaires

- `plus` : `a + b`
- `minus` : `a - b`
- `times` : `a * b`
- `div` : `a / b`
- `rem` : `a % b`
- `rangeTo` : `a..b`
- `contains` : `a in b`

exemple :

```kotlin runnable
data class Ship(var fuel:Int = 0){
    operator fun plus(value:Int){
        this.fuel += value
    }
    operator fun minus(value:Int){
        this.fuel -= value
    }
}

operator fun Ship.dec():Ship{
    this.fuel--
    return this
}

fun main(vararg args:String){
    var ship = Ship()
    println(ship) // expect fuel = 0
    ship + 50
    println(ship) // expect fuel = 50
    ship - 10
    println(ship) // expect fuel = 40
    ship--  // ship reassigned
    println(ship) // expect fuel = 39
    println(--ship) // expect fuel = 38
}
```

exemple : LocalDate + Period

```kotlin runnable
import java.time.LocalDate
import java.time.Period

fun main(vararg args:String){
    println(LocalDate.parse("2017-12-31") + Period.ofDays(1)) // 2018-01-01
} 
```

## Lamda avec récepteur

Le principe de base est une fonction d'extension passée en paramètre d'une méthode. En schématisant, la signature ressemble alors à ça 
`Receiver.(In) -> Out`.

Avec cette fonctionnalité, on s'approche de la puissance du language Kotlin pour la réalisation de **DSL (Domain Spécific Language)**.

La **lamdba avec récepteur** la plus connue, fournie par le language, est `apply` avec l'implémentation suivante :

```kotlin
public inline fun <T> T.apply(block: T.() -> Unit): T { block(); return this }
```

* C'est une fonction d'extension générique qui prend en paramètre une fonction d'extension sur le même *receiver* `T`
* Elle est disponible pour tous les types d'objet
* Son implémentation exécute juste la lambda fournie.
* Elle retourne l'objet récepteur

```kotlin runnable
fun main(vararg args: String) {
    println(
            StringBuilder("One to ten = ")
                    .apply { // this:StringBuilder
                        // code arbitraire …
                        for (i in 1..10) {
                            append(i)
                            append(" ")
                        }
                    }.toString()
    )
}
```

## Scoped functions

Kotlin fournit quelques *fonctions d'extensions* à toutes les objets, très pratiques en facilitant l'écriture et permettant l'enchainement de fonctions :
`apply` (vue précédemment), `let`, `run`, `also` et `with`.

Ces fonctions apportent un objet arbitraire dans un nouveau contexte. Cet objet de contexte est accesible par `it` (ou autre nom défini) ou par `this`.
Sur le principe, ces *scoped functions* se ressemblent beaucoup malgré leurs noms déroutants , avec toutefois quelques subtilités …

### let

[Documentation](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/let.html)

```kotlin 
public inline fun <T, R> T.let(block: (T) -> R): R
``` 

* `let` applique un traitement (lambda) sur un objet `T` et retourne un résultat de transformation `R`
* `let` remplace avantageusement les tests idiomatiques `if(object == null)`

```kotlin
val mapped = value?.let { transform(it) } ?: defaultValue
```

### run

[Documentation](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/run.html)

```kotlin
inline fun <T, R> T.run(block: T.() -> R): R
```

* `run` est sensiblement identique à `let` à l'exception du paramètre *lamdba avec récepteur*. L'accès
à l'objet contexte par `this`.

### also

[Documentation](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/also.html)

```kotlin
inline fun <T> T.also(block: (T) -> Unit): T
```

* `also` permet d'appliquer un traitement sur un objet `T`. À la différence de `let` et `run`, `also` retourne forcément l'objet cible.
* il est très pratique par exemple pour l'initialisation d'objets sans passer par des builders

Par exemple :

```kotlin
fun buildWorkspace():Workspace {
    val workspace = Workspace()
    workspace.name = "Custom Workspace"
    workspace.init()
    return workspace
}
``` 

devient :

```kotlin
fun buildWorkspace() = Workspace()
    .also { 
        it.name = "Custom Workspace"
        it.init() 
    }
```

### apply

[Documentation](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/apply.html)

```kotlin
inline fun <T> T.apply(block: T.() -> Unit): T
```

* `apply` est comme `also`, mais avec une *lambda avec récepteur*, ce qui veut dire que l'objet cible est accessible par `this`

exemple :

```kotlin
fun buildWorkspace() = Workspace()
    .apply { 
        name = "Custom Workspace"
        init() 
    }
```

### with

[Documentation](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/with.html)

```kotlin
inline fun <T, R> with(receiver: T, block: T.() -> R): R
```

* `with` contrairement aux autres, n'est pas une fonction d'extension
* `with` permet de choisir le context `this` et d'isoler une série de variables dans un bloc de code

```kotlin runnable
// { autofold
import java.io.StringWriter
// }

fun main(vararg args:String){
    println(with(StringWriter()){
        val result = Math.cos(Math.PI / 4)
        append("cos(π/4)=")
        append(result.toString())
    })
}
```

## Type aliases

Avec la généricité, arrivent les types de longueur kilométrique. Le mot clé `typealias` permet de simplifier et définir un alias à des types trop complexes.

```kotlin
typealias SpecialList<T> = MutableList<Map<String, List<T>>>
val myList:SpecialList<Long> = mutableListOf()
myList.add(mapOf("Fibonacci" to listOf(1L,3L,5L,8L)))
```

## Quizz

### Question 1

```kotlin
class Parent{
    init {
        println("Hello from Parent")
    }
}

class Child:Parent(){
    init {
        println("Hello from Child")
    }
}
```

?[Qu'affiche la création de Child()]
-[ ] Hello from Child
-[ ] Hello from Parent\nHello from Child
-[X] Erreur de compilation



## Exercices

### unwrap

Écrire la fonction d'extension `unwrap` qui convertit un `Optional` en type nullable

```kotlin runnable
import java.util.Optional

fun <T> Optional<T>.unwrap():T? {
    TODO()
}

fun main(varar params:String){
    println(Optional.ofNullable(null).unwrap())
    println(Optional.of("message").unwrap())
}
```


### Startrek

Créer le modèle métier suivant :

![startrek](Startrek.png)

Construire un Univers avec le vaisseau **Enterprise** et faites le déplacer de la planète **Terre** vers la planète **Vulcain**.

*Logger* cette information lors du déplacement (`println`). 