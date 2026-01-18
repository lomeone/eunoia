package com.lomeone.eunoia.optimizaion

interface FeatureFlag : AutoCloseable {
    fun isEnabled(key: String, context: OptimizationContext? = null): Boolean
}
