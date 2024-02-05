package com.example.noticeboard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@EnableAspectJAutoProxy
@SpringBootApplication
class NoticeboardApplication

fun main(args: Array<String>) {
    runApplication<NoticeboardApplication>(*args)
}
