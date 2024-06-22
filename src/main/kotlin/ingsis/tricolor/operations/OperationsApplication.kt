package ingsis.tricolor.operations

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class OperationsApplication {
    @GetMapping("/")
    fun noAuth(): String {
        println("This is returning the message")
        return "no auth needed"
    }
}

fun main(args: Array<String>) {
    runApplication<OperationsApplication>(*args)
}
