package com.lomeone.eunoia.optimizaion

interface DynamicConfig : AutoCloseable {
    fun getString(key: String, defaultValue: String): String
    fun getLong(key: String, defaultValue: Long): Long
    fun getBoolean(key: String, defaultValue: Boolean): Boolean
    fun <T> getCustom(key: String, clazz: Class<T>, defaultValue: T): T
}

fun interface ChangeListener {
    fun onConfigUpdated(key: String, value: Any)
}

interface ListenableDynamicConfig : DynamicConfig {
    fun addListener(key: String, listener: ChangeListener): AutoCloseable
}
