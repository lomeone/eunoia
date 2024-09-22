package com.eunoia.event.spring.transactional.outbox

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OutboxAsyncRemover(
    private val outboxRepository: OutboxRepository
) : OutboxRemover {
    @Async
    @Transactional
    override fun removeOutboxData(outbox: Outbox) {
        outboxRepository.delete(outbox)
    }
}
