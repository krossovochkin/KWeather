package com.krossovochkin.kweather.setup.data.json

sealed class JsonElement

class JsonObject : JsonElement() {

    val children: MutableMap<String, JsonElement> = mutableMapOf()

    fun add(key: String, element: JsonElement) {
        children[key] = element
    }
}

class JsonArray : JsonElement() {

    val children: MutableList<JsonElement> = mutableListOf()

    fun add(element: JsonElement) {
        children.add(element)
    }
}

data class JsonProperty(
    val value: Any?
) : JsonElement()