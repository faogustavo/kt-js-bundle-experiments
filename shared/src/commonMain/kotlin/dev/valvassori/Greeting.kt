package dev.valvassori

import kotlin.js.JsExport

@JsExport
class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}
