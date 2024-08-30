package com.eunoia.messaging.message

interface Message {
    fun getBody(): String
    fun getHeader(): Map<String, String>
    fun getHeader(key: String): String?
    fun putHeader(key: String, value: String)
}
