package com.m.one.domain.salt

import com.m.one.domain.exception.AlreadyUserException
import com.m.one.domain.model.Salt
import com.m.one.domain.repository.SaltRepository
import com.m.one.domain.service.SaltService
import com.m.one.message.SaltResponse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Mono
import reactor.test.StepVerifier




@SpringBootTest
class SaltServiceTest(
    @Autowired
    private val saltService: SaltService
) {

    @Test
    fun `salt reg test`() {
        val email = "ab@av.com"
        val result = saltService.insert(email)
        StepVerifier.create(result)
            .assertNext {
                it.email = email
            }
            .expectComplete()
            .verify()
    }

    @Test
    fun `salt dupli test`() {
        val email = "abc@av.com"
        val result = saltService.insert(email)
        val result2 = saltService.insert(email)
        StepVerifier.create(result)
            .assertNext {
                it.email = email
            }
            .expectComplete()
            .verify()


        StepVerifier.create(result2)
            .expectError(AlreadyUserException::class.java)
            .verify()
    }

}