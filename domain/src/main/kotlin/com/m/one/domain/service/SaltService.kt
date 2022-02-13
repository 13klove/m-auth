package com.m.one.domain.service

import com.m.one.domain.exception.AlreadyUserException
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

    companion object: KLogging() {
        const val ERROR_MSG = "already user"
    }

    @Transactional
    fun insert(email: String): Mono<SaltResponse> {
        return saltRepository.findByEmail(email)
            .flatMap<SaltResponse?> {
                Mono.error(AlreadyUserException(ERROR_MSG))
            }.switchIfEmpty(
                Mono.defer {
                    val salt = Salt.create(email, UUID.randomUUID().toString())
                    saltRepository.save(salt)
                        .map(Salt::toSaltResponse)
                }
            )
    }

}