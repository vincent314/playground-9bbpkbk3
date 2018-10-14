# Introduction

## Prérequis

Pour bien apréhender ce cours, il est nécessaire de connaître **Java**. Il fera référence à de nombreuses fonctionnalités
Java sur lesquelles nous ne rentrerons pas dans le détail.

Il est fortement recommandé d'utiliser l'IDE **IntelliJ** avec l'excellent plugin Kotlin qui apportera le meilleur support possible du language.

## println("Hello Kotlin")

**Kotlin** est un language développé par les équipes de **JetBrains** et dont le nom fait référence à une île en face de St Peterbourg.

* language pragmatique : **lisibilité**, **réutilisabilité**, **interoperabilité**, **sécurisé** et **outillé**
* language pour l'industrie

Même si Kotlin est surtout un language de la JVM, au même titre que **JAVA**, **Scala**, **Groovy** ou **Closure** il cible désormais le Javascript (transpilation) et le natif (avec LLVM).

* **compatible JVM 6+**
* *first class language* pour **Android**
* produit des fichiers `.class` pour la JVM, mix Java / Kotlin
* **intégration** parfaite dans les projets Java : SpringFramework, Maven, Gradle
* **IDE support** : IntelliJ, Android Studio

## E=mc² : Errors = More Code²

* Kotlin est un language à **typage statique** (comme Java)
* **les fonctions** sont des éléments de premiers plan (au même niveau que les classes)
* **les fonctionnalités de base** de Kotlin recouvrent celles de librairies JAVA puissantes comme *Lombok*, *VAVR* ou *Guava*.
* Stabilité (gestion des **null**, typage …) et performance (inline functions, collections immutables, …)

La syntaxe est plus riche que la syntaxe Java ou Javascript avec de nombreux nouveaux mots clés : `is`, `as`, `override`, `by`, `when` …
Pas de panic, il est toujours possible d'utiliser la syntaxe ```` s'il y a un conflit avec vos variables.

No magic ! Le language apporte nativement beaucoup de *sucre syntaxique* et prend en charge de nombreux pattern
de programmation usuels (singletons, data classes, délégation, programmation fonctionnelle, null safety …).

Le code Kotlin, dépourvu du code superflux, est plus condensé et plus lisible par rapport à Java.

Si on est curieux, on peut **visualiser le code équivalent JAVA** (Kotlin -> Bytecode -> JAVA)

## Pour tester Kotlin

- [https://tech.io](https://tech.io) Tester Kotlin dans ce tutoriel
- [IntelliJ CE / Ultimate](https://www.jetbrains.com/idea/download/), [Android Studio](https://developer.android.com/studio/)
- [https://play.kotlinlang.org](https://play.kotlinlang.org) Editer et exécuter du code Kotlin
- [https://try.kotlinlang.org](https://try.kotlinlang.org) Tutoriaux et exercices en ligne
- Kotlin REPL (dans IntelliJ Tools -> Kotlin -> Kotlin REPL)
- [Projet Java starter](https://github.com/vincent314/kotlin-starter-project) Projet Gradle clé-en-main

## Liens

- [https://kotlinlang.org/](https://kotlinlang.org/) Site officiel Kotlin
- [https://kotlin.link/](https://kotlin.link/) Énorme liste de liens
- [Twitter @Kotlin](https://twitter.com/kotlin)
- [Keynote 2018](https://www.youtube.com/watch?v=PsaFVLr8t4E) La philosophie derrière Kotlin et les nouveautés de la v1.3