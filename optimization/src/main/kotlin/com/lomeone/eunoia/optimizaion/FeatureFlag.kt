package com.lomeone.eunoia.optimizaion

interface FeatureFlag {
    fun isEnabled(key: String, context: OptimizationContext? = null): Boolean
}
