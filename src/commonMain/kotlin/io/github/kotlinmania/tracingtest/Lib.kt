package io.github.kotlinmania.tracingtest

/**
 * Scope provided to test bodies executed with [tracedTest].
 */
public class TracedTestScope(
    public val scopeName: String,
) {
    /**
     * Ensure that a certain string is (or is not) logged anywhere in the logs for this test scope.
     */
    public fun logsContain(value: String): Boolean = logsWithScopeContain(scopeName, value)

    /**
     * Run an assertion function against the log lines captured for this test scope.
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
 * Runs [block] within a traced test context named [scopeName].
 */
public fun <T> tracedTest(
    scopeName: String = "test",
    block: TracedTestScope.() -> T,
): T {
    val scope = TracedTestScope(scopeName)
    return scope.block()
}
