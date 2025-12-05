package org.transaction.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TransactionApiApplication

fun main(args: Array<String>) {
    runApplication<TransactionApiApplication>(*args)
}