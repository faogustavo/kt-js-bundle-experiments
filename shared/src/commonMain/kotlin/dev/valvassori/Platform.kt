package dev.valvassori

import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
interface Platform {
    @JsName("name")
    val name: String
}

expect fun getPlatform(): Platform
