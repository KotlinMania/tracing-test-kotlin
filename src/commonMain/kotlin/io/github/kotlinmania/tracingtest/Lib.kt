package io.github.kotlinmania.tracingtest

/**
 * Scope provided to test bodies executed with [tracedTest].
 *
 * This scope gives test blocks access to the captured log buffer, allowing assertions
 * against log lines produced during test execution.
 */
public class TracedTestScope(
    public val scopeName: String,
) {
    /**
     * Ensure that a certain string is (or is not) logged anywhere in the logs for this test scope.
     *
     * Returns true if the captured log lines associated with [scopeName] contain [value].
     */
    public fun logsContain(value: String): Boolean = logsWithScopeContain(scopeName, value)

    /**
     * Run an assertion function against the log lines captured for this test scope.
     *
     * Passes the captured lines to [block]. If [block] returns a failed [Result],
     * an [AssertionError] or the contained exception is thrown.
     */
    public fun logsAssert(block: (List<String>) -> Result<Unit>) {
        val result = logsAssert(scopeName, block)
        if (result.isFailure) {
            val exception = result.exceptionOrNull()
            if (exception != null) {
                throw exception
            } else {
                throw AssertionError("Log assertion failed for scope $scopeName")
            }
        }
    }
}

/**
 * Helper functions and utilities that allow for easier testing of code that uses tracing.
 *
 * The focus is on testing the logging, not on debugging the tests. That's why the
 * library ensures that the logs do not depend on external state.
 *
 * ## Usage
 *
 * Wrap your test block with [tracedTest]:
 *
 * ```kotlin
 * @Test
 * fun testLogging() = tracedTest("testLogging") {
 *     // Run code that logs events
 *     assertTrue(logsContain("expected message"))
 *     assertFalse(logsContain("unexpected error"))
 * }
 * ```
 *
 * ## Rationale
 *
 * In multiplatform and multi-threaded test environments, logs can be emitted across
 * background coroutines or worker threads. [tracedTest] captures logs emitted within the
 * named test scope into an in-memory buffer so assertions can verify log output reliably.
 *
 * Runs [block] within a traced test context named [scopeName].
 */
public fun <T> tracedTest(
    scopeName: String = "test",
    block: TracedTestScope.() -> T,
): T {
    val scope = TracedTestScope(scopeName)
    return scope.block()
}
