package com.eunoia.event.spring.transactional.outbox.event.kafka

import com.eunoia.event.EventProducer
import io.cloudevents.CloudEvent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Component
class KafkaProducer(
    private val kafkaOutboxRepository: KafkaOutboxRepository
) : EventProducer {
    @Transactional
    override fun produce(destination: String, event: CloudEvent) {
        val kafkaOutbox = kafkaOutboxRepository.save(KafkaOutbox(
            topic = destination,
            partitionKey = PARTITION_KEY,
            messageBody = event.data.toString(),
            messageHeaders = Json.encodeToString(event)
        ))
    }
}
