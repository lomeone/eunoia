package com.eunoia.event.spring.kafka

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.IntegerDeserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory

const val PARTITION_KEY = ""

@EnableKafka
@Configuration
class SpringKafkaConfig {

    @Bean
    fun kafkaListenerContainerFactory(consumerFactory: ConsumerFactory<Int, String>) =
        ConcurrentKafkaListenerContainerFactory<Int, String>().also { it.consumerFactory = consumerFactory }

    @Bean
    fun consumerFactory() = DefaultKafkaConsumerFactory<Int, String>(consumerProps)

    val consumerProps = mapOf(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
        ConsumerConfig.GROUP_ID_CONFIG to "group",
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to IntegerDeserializer::class.java,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest"
    )
}
