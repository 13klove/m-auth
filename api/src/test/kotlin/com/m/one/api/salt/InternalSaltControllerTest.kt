package com.m.one.api.salt

import com.fasterxml.jackson.databind.ObjectMapper
import com.m.one.message.salt.SaltRequest
import com.m.one.message.salt.SaltResponse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

@SpringBootTest
class InternalSaltControllerTest {

    @Test
    fun test() {
        val objectMapper = ObjectMapper()
        val userId = "123"
        val param = objectMapper.writeValueAsString(SaltRequest(userId))
        WebClient.create("http://localhost:8080/internal/salts")
            .post()
            .body(BodyInserters.fromValue(param))
            .retrieve()
            .bodyToMono(SaltResponse::class.java)
            .map {
                Assertions.assertEquals(it.email, userId)
            }
    }

}