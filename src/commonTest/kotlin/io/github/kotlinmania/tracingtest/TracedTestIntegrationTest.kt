package io.github.kotlinmania.tracingtest

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TracedTestIntegrationTest {
    @BeforeTest
    fun setUp() {
        globalBuf().clear()
        INITIALIZED = false
    }

    @Test
    fun testLogsAreCapturedFlow() {
        val testScope = "test_logs_are_captured"
        val buffer = globalBuf()
        val writer = MockWriter.new(buffer)
        val dispatch = getSubscriber(writer, "my_crate=trace")

        assertEquals("my_crate=trace", dispatch.subscriber.envFilter)

        writer.write("INFO $testScope: This is being logged on the info level\n".encodeToByteArray())
        writer.write("WARN $testScope: This is being logged on the warn level from a spawned task\n".encodeToByteArray())

        assertTrue(logsWithScopeContain(testScope, "logged on the info level"))
        assertTrue(logsWithScopeContain(testScope, "logged on the warn level"))
        assertFalse(logsWithScopeContain(testScope, "logged on the error level"))

        val assertResult =
            logsAssert(testScope) { lines ->
                val matching = lines.filter { it.contains("logged") }
                if (matching.size == 2) {
                    Result.success(Unit)
                } else {
                    Result.failure(IllegalStateException("Expected two matching logs, but found ${matching.size}"))
                }
            }

        assertTrue(assertResult.isSuccess)
    }

    @Test
    fun testPlainOldTestFlow() {
        val testScope = "plain_old_test"
        val writer = MockWriter.new(globalBuf())

        assertFalse(logsWithScopeContain(testScope, "Logging from a non-async test"))
        writer.write("INFO $testScope: Logging from a non-async test\n".encodeToByteArray())
        assertTrue(logsWithScopeContain(testScope, "Logging from a non-async test"))
        assertFalse(logsWithScopeContain(testScope, "This was never logged"))
    }

    @Test
    fun testTracedTestDsl() {
        tracedTest("custom_test_scope") {
            val writer = MockWriter.new(globalBuf())
            writer.write("INFO custom_test_scope: Scoped DSL log entry\n".encodeToByteArray())

            assertTrue(logsContain("Scoped DSL log entry"))
            assertFalse(logsContain("Unknown log entry"))

            logsAssert { lines ->
                if (lines.any { it.contains("Scoped DSL") }) {
                    Result.success(Unit)
                } else {
                    Result.failure(AssertionError("Missing expected entry"))
                }
            }
        }
    }
}
