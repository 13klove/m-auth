package com.m.one.domain.service.token

import com.m.one.domain.model.token.TokenType
import com.m.one.domain.model.token.TokenType.BASE
import com.m.one.domain.model.token.TokenType.REFRESH
import com.m.one.domain.repository.SaltRepository
import com.m.one.message.token.PairTokenResponse
import com.m.one.message.token.TokenDetailMessage
import com.m.one.message.token.TokenResponse
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.lang.RuntimeException
import java.util.*

@Service
class TokenService(
    @Value("\${token.expired.base}")
    private val baseExpired: Long,
    @Value("\${token.expired.refresh}")
    private val refreshExpired: Long,
    private val saltRepository: SaltRepository
) {

    companion object: KLogging() {
        const val ERROR_MESSAGE = "no userid"
        const val ERROR_SALT = "no salt"
        const val ERROR_TOKEN_TYPE = "token type error"
        const val ERROR_EXPIRE = "token expire"
        const val ERROR_SECRET = "token secret"
    }

    fun reissueToken(userId: Long, token: String): Mono<PairTokenResponse> {
        val refreshToken = revoke(userId, token, REFRESH)
        return refreshToken.flatMap {
            getBaseTokenAndRefreshToken(userId, it.email)
        }
    }

    fun getBaseTokenAndRefreshToken(userId: Long, email: String): Mono<PairTokenResponse> {
        val baseToken = getToken(userId, email, BASE)
        val refreshToken = getToken(userId, email, REFRESH)
        return Mono.zip(baseToken, refreshToken) { base, refresh ->
            PairTokenResponse(base, refresh)
        }
    }

    private fun getToken(userId: Long, email: String, tokenType: TokenType): Mono<TokenResponse> {
        fun getToken(salt: String): TokenResponse {
            val now = Date()
            val detail = mutableMapOf<String, Any>("email" to email)
            val expired = when(tokenType) {
                BASE -> baseExpired
                REFRESH -> refreshExpired
            }
            logger.info { "$userId, $email, $tokenType" }
            val token = Jwts.builder()
                .setHeaderParam("tokenType", tokenType)
                .setClaims(detail)
                .setIssuedAt(now)
                .setExpiration(Date(now.time + expired))
                .signWith(SignatureAlgorithm.HS256, salt)
                .compact()
            return TokenResponse(tokenType.name, token)
        }

        return saltRepository.findByUserIdAndDeletedAtIsNull(userId)
            .map {
                getToken(it.salt)
            }.switchIfEmpty(Mono.error(RuntimeException(ERROR_SALT)))
    }

    fun revoke(userId: Long, token: String, tokenType: TokenType): Mono<TokenDetailMessage> {
        return saltRepository.findByUserIdAndDeletedAtIsNull(userId)
            .switchIfEmpty(Mono.error(RuntimeException(ERROR_MESSAGE)))
            .map {
                try {
                    val claims = Jwts.parser()
                        .setSigningKey(it.salt)
                        .parseClaimsJws(token)

                    val header = claims.header
                    val body = claims.body

                    if (header["tokenType"] != tokenType.name) {
                        throw RuntimeException(ERROR_TOKEN_TYPE)
                    }

                    val email = body["email"] as String
                    val iat = body["iat"] as Int
                    val exp = body["exp"] as Int
                    TokenDetailMessage(tokenType.name, token, email, iat, exp)
                } catch (e: ExpiredJwtException) {
                    throw RuntimeException(ERROR_EXPIRE)
                } catch (e: IllegalArgumentException) {
                    throw RuntimeException(ERROR_SECRET)
                }
            }
    }

}