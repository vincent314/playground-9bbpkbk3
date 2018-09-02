# Classes et Objets

## Déclaration et instanciation

Les méthodes et attributs sont par défaut **public**. Des getters (`val` / `var`) et setters (`var`) sont
générés pour les attributs. Les méthodes

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

Pour qu'une classe hérite d'une classe parente, cette dernière doit être taggé `open` (les classes sont `final` par défaut)

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
les classes de modèle ou **data classes**. C'est une fonctionnalité très similaire à l'annotation `@Data` de **Lombok**

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

```kotlin
data class Person( var firstName:String, var lastName:String, var birthDate:Date)


```

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

Cependant, depuis Java (voir la décompilation de la classe), l'appel devra se faire ainsi `HasStaticMembers.Companion.doSomething()`. 
Pour les utilisateurs Java de votre librairie, ajoutez l'annotation `@JvmStatic` à vos membres statiques `HasStaticMembers.doSomething()` 

**Exercice** : Comparer les deux versions Java générées avec ou sans `@JvmStatic`

## Fonctions d'extension

Sans avoir à créer une nouvelle classe pour étendre la liste de méthodes d'une classe, nous pouvons créer des **fonctions d'extension**.
Au sein de cette **fonction d'extension**, nous avons accès aux attributs membres de la classe en passant par la variable `this`

```kotlin runnable
// { autofold
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
// }

fun Date.toLocalDate(): LocalDate = Instant.ofEpochMilli(this.time).atZone(ZoneId.systemDefault()).toLocalDate()

fun main(vararg args:String){
    println(Date().toLocalDate())
}
``` 

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