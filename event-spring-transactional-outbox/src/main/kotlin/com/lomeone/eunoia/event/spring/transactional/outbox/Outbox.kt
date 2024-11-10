package com.lomeone.eunoia.event.spring.transactional.outbox

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import java.time.LocalDateTime.now

const val PARTITION_KEY: String = ""

@Entity
class Outbox(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val topic: String,
    val key: String,
    val payload: String,
    val attributes: String
) {
    @CreatedDate
    @Column(updatable = false)
    val createdAt: LocalDateTime = now()

    @LastModifiedDate
    var updatedAt: LocalDateTime = now()
        protected set
}
