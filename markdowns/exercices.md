# Exercice

## Additions Romaines

Écrivez une fonction d'ajout de deux nombres romains en utilisant le projet 
[https://github.com/vincent314/kotlin-training](https://github.com/vincent314/kotlin-training) 
et les tests unitaires fournis.

⚠ Vous ne devez **jamais** convertir les nombres en décimal !

Utilisez les algorithmes très simples (ex. chercher / remplacer), et limitez vous au milliers. 

### Exemples

* `sum("X","XI") == "XVI"`
* `sum("DCXCIV", "MMCCCLIV") == "MMMXLVIII"`

### Rappels

* 1 = I
* 4 = IV
* 5 = V
* 9 = IX
* 10 = X
* 40 = XL
* 50 = L
* 100 = C
* 400 = CD
* 500 = D
* 900 = CM
* 1000 = M

### Étapes

#### Décompacter
 
Construisez d'abord une fonction `unpack` pour décompacter les nombres : écrire de façon naïve les formes soustractives.

* `unpack("XIX") == "XVIIII"`
* `unpack("XXIV") == "XXIIII"`

#### Concaténer et trier

Construisez une fonction `merge` pour concaténer et trier les caractères.

* `merge("XVIIII", "XXIIII") == "XXXVIIIIIIII""`

#### Regrouper 

Construisez une fonction `group` pour regrouper les "tas" en partant de la droite (les plus petits).

* `group("XXXVIIIIIIII) == "XXXXIII"`

#### Simplifier

Construisez une fonction `simplify` pour simplifiers les formes soustractives XL ou IV :

* `simplify("XXXXIII") == "XLIII"`

#### Sommer

Composer les 4 méthodes précédentes dans une fonction `sum`

* `sum("XIX", "XXIV") == "XLIII"`