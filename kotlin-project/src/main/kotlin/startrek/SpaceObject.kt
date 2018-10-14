package startrek

import java.io.Serializable

abstract class SpaceObject(var position: Position) : Serializable {
    constructor(planet: Planet) : this(planet.position)
}