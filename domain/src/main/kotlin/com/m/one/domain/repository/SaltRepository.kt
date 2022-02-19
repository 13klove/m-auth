package com.m.one.domain.repository

import com.m.one.domain.model.salt.Salt
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface SaltRepository: ReactiveMongoRepository<Salt, String> {

    fun findByUserIdAndDeletedAtIsNull(userId: Long): Mono<Salt>

}