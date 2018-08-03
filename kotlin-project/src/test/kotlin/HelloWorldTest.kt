import org.junit.Assert.assertEquals
import org.junit.Test

class HelloWorldTest {

    @Test
    fun shouldReturnHelloWorld() {
        assertEquals(hello(), "hello world")
    }
}