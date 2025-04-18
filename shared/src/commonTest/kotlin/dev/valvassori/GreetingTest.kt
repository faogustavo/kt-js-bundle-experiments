package dev.valvassori

import dev.valvassori.Greeting
import kotlin.test.Test
import kotlin.test.assertTrue

class GreetingTest {
    @Test
    fun testGreeting() {
        val subject = Greeting().greet()

        assertTrue(subject.startsWith("Hello,"), "Greeting should start with 'Hello,'")
        assertTrue(subject.endsWith("!"), "Greeting should end with '!'")
    }
}
