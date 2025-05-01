package dev.valvassori.ext

/**
 * Formats an integer price value in cents to a dollar string representation.
 * For example, 1295 becomes "$12.95"
 */
fun Int.formatAsMoney(): String {
    val dollars = this / 100
    val cents = this % 100
    return "$$dollars.${cents.toString().padStart(2, '0')}"
} 