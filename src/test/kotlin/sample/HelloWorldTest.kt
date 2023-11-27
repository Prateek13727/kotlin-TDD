package sample

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.invoking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldThrow
import org.amshove.kluent.withMessage
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

internal class HelloWorldTest {
    @Test
    internal fun `should print hello world`() {
        val helloWorld = HelloWorld()

        val message = helloWorld.run()

        message shouldBeEqualTo "Hello World!"
    }

    @Test
    internal fun `use of mock`() {
        val helloWorldMock: HelloWorld = mock()

        whenever(helloWorldMock.run()) doReturn "Hello World!"

        verify(helloWorldMock, times(0)).run()
    }

    @Test
    internal fun `test for coroutines`() = runBlockingTest {
        val helloWorld = HelloWorld()

        val message = helloWorld.sayHello("tester")

        message shouldBeEqualTo "Hello tester"
    }

    @Test
    internal fun `test for coroutines for exception`() = runBlockingTest {
        val helloWorld = HelloWorld()

        invoking { runBlocking { helloWorld.sayHello(null) } } shouldThrow RuntimeException::class withMessage "name is null"
    }
}