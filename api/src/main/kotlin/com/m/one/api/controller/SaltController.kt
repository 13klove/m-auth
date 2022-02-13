package com.m.one.api.controller

import com.m.one.api.controller.SaltController.Companion.BASE_URL
import com.m.one.domain.service.SaltService
import com.m.one.message.SaltRequest
import com.m.one.message.SaltResponse
import mu.KLogging
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("$BASE_URL")
class SaltController(
    private val saltService: SaltService
) {

    companion object: KLogging() {
        const val BASE_URL = "salts"
    }

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun reg(@RequestBody saltRequest: SaltRequest): Mono<SaltResponse> {
        logger.info { saltRequest }
        return saltService.insert(saltRequest.email)
    }

}