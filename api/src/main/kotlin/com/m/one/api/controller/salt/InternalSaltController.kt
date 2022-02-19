package com.m.one.api.controller.salt

import com.m.one.api.controller.salt.InternalSaltController.Companion.BASE_URL
import com.m.one.domain.service.salt.SaltService
import com.m.one.message.salt.SaltRequest
import com.m.one.message.salt.SaltResponse
import mu.KLogging
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("$BASE_URL")
class InternalSaltController(
    private val saltService: SaltService
) {

    companion object: KLogging() {
        const val BASE_URL = "/internal/salt"
    }

    @PostMapping
    fun reg(@RequestBody saltRequest: SaltRequest): Mono<SaltResponse> {
        logger.info { "url: /salts, method: post, param: $saltRequest" }
        return saltService.insert(saltRequest.userId)
    }

}