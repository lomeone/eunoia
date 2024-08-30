package org.example.com.eunoia.event

interface Event {
    fun getBody(): String
    fun getHeaders(): Map<String, String>
    fun getHeader(key: String): String?
    fun putHeader(key: String, value: String)
}
