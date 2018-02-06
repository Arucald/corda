package net.corda.nodeapi.internal

import com.google.common.cache.CacheBuilder
import java.time.Duration
import java.util.concurrent.TimeUnit

class ArtemisDeduplicationCache(expiry: Duration) {
    private val messageIdCache =
            CacheBuilder.newBuilder()
                    .expireAfterAccess(expiry.toNanos(), TimeUnit.NANOSECONDS)
                    .build<Long, Unit>()

    fun checkDuplicateMessageId(messageId: Long): Boolean {
        var isDuplicate = true
        messageIdCache.get(messageId) {
            isDuplicate = false
        }
        return isDuplicate
    }
}
