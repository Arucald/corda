package net.corda.nodeapi.internal

import java.util.concurrent.atomic.AtomicLong

class ArtemisDeduplicationChecker {
    private val watermark = AtomicLong(-1)

    fun checkDuplicateMessageId(messageId: Long): Boolean {
        return watermark.getAndUpdate { maxOf(messageId, it) } >= messageId
    }
}
