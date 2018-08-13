# Introduction

## Hello Kotlin

En guise d'accueil dans cette formation, commençons pas le traditionnel **Hello World**

```kotlin runnable
fun main(args: Array<String>) {
    println("Hello, World!")
}
```

## Syntaxe

La syntaxe est plus riche que la syntaxe Java ou Javascript avec de nombreux nouveaux mots clés : `is`, `as`, `override`, `by`, `when` …
Pas de panic, il est toujours possible d'utiliser la syntaxe ```` s'il y a un conflit avec vos variables.

No magic ! Le language apporte nativement beaucoup de *sucre syntaxique* et prend en charge de nombreux pattern
de programmation usuels (singletons, data classes, délégation, programmation fonctionnelle, null safety …).

Le code Kotlin, dépourvu du code superflux, est plus condensé et plus lisible par rapport à Java.

Si on est curieux, on peut visualiser le code équivalent JAVA (Kotlin -> Bytecode -> JAVA)

## Pour tester Kotlin

- [https://tech.io](https://tech.io)
- IntelliJ
- [https://try.kotlinlang.org](https://try.kotlinlang.org)
- Kotlin REPL (dans IntelliJ Tools -> Kotlin -> Kotlin REPL)

## Quizz

?[Avec Kotlin, on peut générer :]
- [ ] du Java
- [X] du Javascript
- [ ] du CSS
- [X] du code natif
- [X] du bytecode JVM