package com.m.one.api.controller.token

import com.m.one.api.controller.token.InternalTokenController.Companion.BASE_URL
import com.m.one.domain.model.token.TokenType
import com.m.one.domain.service.token.TokenService
import com.m.one.message.token.PairTokenResponse
import com.m.one.message.token.RevokeRequest
import com.m.one.message.token.TokenDetailMessage
import com.m.one.message.token.TokenRequest
import mu.KLogging
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("$BASE_URL")
class InternalTokenController(
    private val tokenService: TokenService
) {

    companion object : KLogging() {
        const val BASE_URL = "/internal/token"
    }

    @PostMapping("/revoke")
    fun revoke(
        @RequestBody revokeRequest: RevokeRequest,
    ): Mono<TokenDetailMessage> {
        return tokenService.revoke(revokeRequest.userId, revokeRequest.token, TokenType.BASE)
    }

    @PostMapping
    fun getToken(
        @RequestBody tokenRequest: TokenRequest
    ): Mono<PairTokenResponse> {
        return with(tokenRequest) {
            tokenService.getBaseTokenAndRefreshToken(userId, email)
        }
    }

}