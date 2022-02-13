package com.m.one.api.salt

import com.fasterxml.jackson.databind.ObjectMapper
import com.m.one.message.SaltRequest
import com.m.one.message.SaltResponse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

@SpringBootTest
class SaltControllerTest {

    @Test
    fun test() {
        val objectMapper = ObjectMapper()
        val mail = "cba@webflux.in"
        val param = objectMapper.writeValueAsString(SaltRequest(mail))
        WebClient.create("http://localhost:8080/salts")
            .post()
            .body(BodyInserters.fromValue(param))
            .retrieve()
            .bodyToMono(SaltResponse::class.java)
            .map {
                Assertions.assertEquals(it.email, mail)
            }
    }

}