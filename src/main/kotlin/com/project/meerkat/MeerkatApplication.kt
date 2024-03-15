package com.project.meerkat

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@MapperScan(basePackages = ["com.project.meerkat"])
class MeerkatApplication

fun main(args: Array<String>) {
    runApplication<MeerkatApplication>(*args)
}
