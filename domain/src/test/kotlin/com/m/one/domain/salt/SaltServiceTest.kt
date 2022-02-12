package com.m.one.domain.salt

import com.m.one.domain.model.Salt
import com.m.one.domain.repository.SaltRepository
import com.m.one.domain.service.SaltService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.test.StepVerifier




@SpringBootTest
class SaltServiceTest(
    @Autowired
    private val saltService: SaltService,
    @Autowired
    private val saltRepository: SaltRepository
) {

    @Test
    fun save() {
        saltRepository.save(Salt.create("a", "b")).subscribe()
    }

    @Test
    fun insertTest() {
        val aa = saltService.insert("ab@av.com")
    }

}