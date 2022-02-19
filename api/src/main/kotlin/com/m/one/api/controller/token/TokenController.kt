package com.m.one.api.controller.token

import com.m.one.api.controller.token.TokenController.Companion.BASE_URL
import com.m.one.domain.model.token.TokenType.BASE
import com.m.one.domain.service.token.TokenService
import com.m.one.message.token.PairTokenResponse
import com.m.one.message.token.TokenDetailMessage
import mu.KLogging
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("$BASE_URL")
class TokenController(
    private val tokenService: TokenService
) {

    companion object : KLogging() {
        const val BASE_URL = "token"
        const val AUTHORIZATION_KEY = "Authorization"
        const val USER_ID_KEY = "X-USER-ID"
    }

    @PostMapping("/revoke")
    fun revoke(
        @RequestHeader(AUTHORIZATION_KEY) token: String,
        @RequestHeader(USER_ID_KEY) userId: Long,
    ): Mono<TokenDetailMessage> {
        return tokenService.revoke(userId, token, BASE)
    }

    @PostMapping
    fun getToken(
        @RequestHeader(AUTHORIZATION_KEY) token: String,
        @RequestHeader(USER_ID_KEY) userId: Long,
    ): Mono<PairTokenResponse> {
        return tokenService.reissueToken(userId, token)
    }

}