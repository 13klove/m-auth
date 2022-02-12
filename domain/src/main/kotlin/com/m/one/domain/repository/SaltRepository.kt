package com.m.one.domain.repository

import com.m.one.domain.model.Salt
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface SaltRepository: ReactiveMongoRepository<Salt, String> {

}