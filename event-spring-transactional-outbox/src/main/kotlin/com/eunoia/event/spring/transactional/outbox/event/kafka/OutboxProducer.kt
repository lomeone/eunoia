package com.eunoia.event.spring.transactional.outbox.event.kafka

import com.eunoia.event.EventProducer
import io.cloudevents.CloudEvent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class OutboxProducer(
    private val outboxRepository: OutboxRepository
) : EventProducer {
    @Transactional
    override fun produce(destination: String, event: CloudEvent) {
        val metadataKeys = event.extensionNames
        val metadata: MutableMap<String, Any> = mutableMapOf()

        for (key in metadataKeys) {
            event.getExtension(key)?.let {
                metadata.put(key, it)
            }
        }

        val outbox = outboxRepository.save(Outbox(
            topic = destination,
            key = PARTITION_KEY,
            payload = event.toString(),
            metadata = Json.encodeToString(metadata)
        ))
    }
}
