package com.lomeone.eunoia.event.spring.transactional.outbox

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
class OutboxPostProduceProcessor(
    private val outboxRemover: OutboxRemover
) {

    private val logger = LoggerFactory.getLogger(OutboxPostProduceProcessor::class.java)

    @TransactionalEventListener
    fun processOnOutboxProduce(outbox: Outbox) {
        try {
            outboxRemover.removeOutboxData(outbox)
        } catch (e: Exception) {
            logger.error("Failed remove outbox message {}", e.toString())
        }
    }
}
