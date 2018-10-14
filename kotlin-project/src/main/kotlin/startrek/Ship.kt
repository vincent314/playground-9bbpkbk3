package startrek

import java.util.*

open class Ship(var name: String, var crew: Long, position: Position) : SpaceObject(position) {

    constructor(name: String, crew: Long, planet: Planet) : this(name, crew, planet.position)

    companion object {
        fun log(message: String) {
            println("Captain's log, star date ${Date().time} : $message")
        }
    }

    fun go(position: Position) {
        log("Going to position $position")
    }

    fun go(planet: Planet) {
        log("Going to planet ${planet.name}")
    }
}