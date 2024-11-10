package com.lomeone.eunoia.event.spring.transactional.outbox

import com.lomeone.eunoia.event.EventProducer
import io.cloudevents.CloudEvent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class OutboxProducer(
    private val outboxRepository: OutboxRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) : EventProducer {
    @Transactional
    override fun produce(destination: String, event: CloudEvent) {
        val metadataKeys = event.extensionNames
        val attributes: MutableMap<String, Any> = mutableMapOf()

        for (key in metadataKeys) {
            event.getExtension(key)?.let {
                attributes.put(key, it)
            }
        }

        val outbox = outboxRepository.save(
            Outbox(
                topic = destination,
                key = PARTITION_KEY,
                payload = event.toString(),
                attributes = Json.encodeToString(attributes)
            )
        )

        applicationEventPublisher.publishEvent(outbox)
    }
}
