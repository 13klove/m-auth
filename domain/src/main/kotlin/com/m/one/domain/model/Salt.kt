package com.m.one.domain.model

import com.m.one.domain.repository.SaltRepository
import com.m.one.message.SaltResponse
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document
class Salt(
    @Id
    var id: String? = null,
    var email: String,
    var salt: String,
    var createdAt: Long,
    var updatedAt: Long,
    var deletedAt: Long? = null
) {

    companion object {
        fun create(email: String, salt: String): Salt {
            val timestamp = Instant.now().toEpochMilli()
            return Salt(null, email, salt, timestamp, timestamp)
        }
    }

    fun toSaltResponse(): SaltResponse {
        return SaltResponse(id!!, email, salt)
    }

}