package com.lomeone.eunoia.event.spring.transactional.outbox

interface OutboxRemover {
    fun removeOutboxData(outbox: Outbox)
}
