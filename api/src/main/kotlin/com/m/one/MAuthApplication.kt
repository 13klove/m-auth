package com.m.one

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
class MAuthApplication

fun main(args: Array<String>) {
    runApplication<MAuthApplication>(*args)
}