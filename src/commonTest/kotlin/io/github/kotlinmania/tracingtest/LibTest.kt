// port-lint: tests tracing-test/src/lib.rs
package io.github.kotlinmania.tracingtest

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class LibTest {
    @Test
    fun testTracedTestBlock() {
        val result =
            tracedTest("customScope") {
                assertEquals("customScope", scopeName)
                assertFalse(logsContain("nonexistent_event"))
                42
            }
        assertEquals(42, result)
    }
}
