package io.github.kotlinmania.tracingtest

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class InternalTest {
    @BeforeTest
    fun setUp() {
        globalBuf().clear()
        INITIALIZED = false
    }

    @Test
    fun testInitializationFlag() {
        assertFalse(INITIALIZED)
        var runCount = 0
        Initialized.callOnce {
            runCount++
        }
        assertEquals(1, runCount)
        assertTrue(INITIALIZED)

        Initialized.callOnce {
            runCount++
        }
        assertEquals(1, runCount)

        INITIALIZED = false
        assertFalse(INITIALIZED)
    }

    @Test
    fun testLogsWithScopeContain() {
        val testScope = "test_my_func"
        globalBuf().write("2026-08-24T00:00:00Z INFO $testScope: This is being logged on the info level\n")
        globalBuf().write("2026-08-24T00:00:00Z WARN $testScope: This is being logged on the warn level\n")
        globalBuf().write("2026-08-24T00:00:00Z INFO other_scope: Other log message\n")

        assertTrue(logsWithScopeContain(testScope, "logged on the info level"))
        assertTrue(logsWithScopeContain(testScope, "logged on the warn level"))
        assertFalse(logsWithScopeContain(testScope, "logged on the error level"))
        assertFalse(logsWithScopeContain(testScope, "Other log message"))
        assertFalse(logsWithScopeContain("nonexistent_scope", "logged on the info level"))
    }

    @Test
    fun testLogsAssert() {
        val testScope = "test_assert_scope"
        globalBuf().write("INFO $testScope: logged message 1\n")
        globalBuf().write("WARN $testScope: logged message 2\n")
        globalBuf().write("INFO other_scope: logged message 3\n")

        val result =
            logsAssert(testScope) { lines ->
                val count = lines.filter { line -> line.contains("logged message") }.size
                if (count == 2) {
                    Result.success(Unit)
                } else {
                    Result.failure(AssertionError("Expected 2 logs but got $count"))
                }
            }

        assertTrue(result.isSuccess)
    }
}
