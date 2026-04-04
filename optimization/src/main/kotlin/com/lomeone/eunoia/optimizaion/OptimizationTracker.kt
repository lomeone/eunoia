package com.lomeone.eunoia.optimizaion

interface OptimizationTracker : AutoCloseable {
    fun trackEvent(eventName: String, context: OptimizationContext, properties: EventProperties = EventProperties())
}

data class EventProperties(
    val data: Map<String, Any> = emptyMap()
) {
    companion object {
        operator fun invoke(block: Builder.() -> Unit): EventProperties {
            val builder = Builder().apply(block)
            return EventProperties(builder.toMap())
        }
    }

    class Builder {
        private val entries = mutableMapOf<String, Any>()

        fun set(key: String, value: Any) {
            entries[key] = value
        }

        internal fun toMap() = entries.toMap()
    }

    override fun toString(): String = data.toString()
}
