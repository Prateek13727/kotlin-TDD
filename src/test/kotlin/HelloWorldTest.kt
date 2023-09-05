import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class HelloWorldTest {
    @Test
    fun `should print hello world`(){
        assertEquals("Hello World!", HelloWorld().run())
    }
}