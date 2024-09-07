package com.eunoia.event.spring.transactional.outbox.event.kafka

import com.eunoia.event.EventProducer
import io.cloudevents.CloudEvent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Component
class OutboxProducer(
    private val kafkaOutboxRepository: OutboxRepository
) : EventProducer {
    @Transactional
    override fun produce(destination: String, event: CloudEvent) {
        val outbox = kafkaOutboxRepository.save(Outbox(
            topic = destination,
            key = PARTITION_KEY,
            payload = event.toString(),
            metadata = Json.encodeToString(event)
        ))
    }
}
