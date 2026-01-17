package com.lomeone.eunoia.optimizaion

data class OptimizationContext(
    val targetId: String,
    val attributes: Map<String, Any> = emptyMap()
) {
    inline fun <reified T> getAttribute(key: String): T? = attributes[key] as? T
}
