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