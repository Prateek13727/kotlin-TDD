package sample

import java.lang.RuntimeException

class HelloWorld {
    fun run(): String {
        return "Hello World!"
    }

    suspend fun sayHello(name: Any?): String {
        if (name == null) {
            throw RuntimeException("name is null")
        }
        return "Hello $name"
    }

}
