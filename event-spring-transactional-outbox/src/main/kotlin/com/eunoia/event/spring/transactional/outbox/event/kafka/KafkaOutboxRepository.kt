package com.eunoia.event.spring.transactional.outbox.event.kafka

import org.springframework.data.jpa.repository.JpaRepository

interface KafkaOutboxRepository : JpaRepository<KafkaOutbox, Long> {
}
