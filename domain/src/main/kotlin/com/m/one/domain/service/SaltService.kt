package com.m.one.domain.service

import com.m.one.domain.model.Salt
import com.m.one.domain.repository.SaltRepository
import com.m.one.message.SaltResponse
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import java.util.*

@Service
class SaltService(
    private val saltRepository: SaltRepository
) {

    companion object: KLogging()

    @Transactional
    fun insert(email: String): Mono<SaltResponse> {
        val salt = Salt.create(email, UUID.randomUUID().toString())
        return saltRepository.save(salt)
            .map(Salt::toSaltResponse)
    }

}