package startrek

fun main(vararg args: String) {
    val earth = Planet("Earth", Position("blah", "blah", "blah"))
    val vulcan = Planet("Vulcan", Position("blah", "blah", "blah"))
    val ussEnterprise = Ship("Enterprise", 400, earth)

    Universe.spaceObjects.addAll(arrayOf(earth, ussEnterprise, vulcan))

    ussEnterprise.go(vulcan)
}