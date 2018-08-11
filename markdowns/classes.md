# Classes et Objets

## Héritage

Pour qu'une classe hérite d'une classe parente :

```kotlin runnable
class Mother{
    var name:String = "Mother"
}
class Daughter:Mother(){
    var address:String = ""
}

fun  main(args:Array<String>) {
    val d = Daugter()
    d.name = "Manue"
    d.address = "1, rue Jean Jaurès, Gueret"
    println(d)
}
```