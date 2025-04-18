package dev.valvassori

import kotlinx.browser.window
import org.w3c.dom.Window

class JSPlatform : Platform {
    override val name: String = "Kotlin JS: ${windowOrNull?.navigator?.platform}"
}

@JsExport
actual fun getPlatform(): Platform = JSPlatform()

private val windowOrNull: Window?
    get() = runCatching { window }.getOrNull()
