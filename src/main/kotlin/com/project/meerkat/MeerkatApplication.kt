package com.project.meerkat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.project.meerkat"])
class MeerkatApplication

fun main(args: Array<String>) {
    runApplication<MeerkatApplication>(*args)
}
