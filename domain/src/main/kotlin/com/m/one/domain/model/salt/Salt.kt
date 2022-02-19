package com.m.one.domain.model.salt

import com.m.one.message.salt.SaltMessage
import com.m.one.message.salt.SaltResponse
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document
class Salt(
    @Id
    var id: String? = null,
    var userId: Long,
    var salt: String,
    var createdAt: Long,
    var updatedAt: Long,
    var deletedAt: Long? = null
) {

    companion object {
        fun create(userId: Long, salt: String): Salt {
            val timestamp = Instant.now().toEpochMilli()
            return Salt(null, userId, salt, timestamp, timestamp)
        }
    }

    fun toSaltResponse(): SaltResponse {
        return SaltResponse(id!!, salt)
    }

    fun toSaltMessage(): SaltMessage {
        return SaltMessage(salt)
    }

}