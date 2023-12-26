package com.carlyu.apibackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ApiBackendApplication

fun main(args: Array<String>) {
    runApplication<ApiBackendApplication>(*args)
}
