// port-lint: source src/internal.rs
package io.github.kotlinmania.tracingtest

/**
 * Static variable state ensuring logging is initialized only once.
 */
public object Initialized {
    private var isInit: Boolean = false

    /**
     * Return whether logging has been initialized.
     */
    public fun isInitialized(): Boolean = isInit

    /**
     * Execute [block] only once.
     */
    public fun callOnce(block: () -> Unit) {
        if (!isInit) {
            isInit = true
            block()
        }
    }

    /**
     * Reset initialization state for testing.
     */
    public fun reset() {
        isInit = false
    }
}

/**
 * Static variable to ensure that logging is only initialized once.
 */
public var INITIALIZED: Boolean
    get() = Initialized.isInitialized()
    set(value) {
        if (value) {
            Initialized.callOnce {}
        } else {
            Initialized.reset()
        }
    }

private val GLOBAL_BUF: LogBuffer = LogBuffer()

/**
 * The global log output buffer used in tests.
 */
public fun globalBuf(): LogBuffer = GLOBAL_BUF

/**
 * Return whether the logs with the specified scope contain the specified value.
 *
 * This function should usually not be used directly, instead use the logs contain
 * function injected by the traced test runner.
 */
public fun logsWithScopeContain(
    scope: String,
    `val`: String,
): Boolean {
    val logs = globalBuf().toUtf8String()
    for (line in logs.split('\n')) {
        if (line.contains(" $scope:") && line.contains(`val`)) {
            return true
        }
    }
    return false
}

/**
 * Run a function against a slice of logs for the specified scope and return its result.
 *
 * This function should usually not be used directly, instead use the logs assert function.
 */
public fun <R> logsAssert(
    scope: String,
    f: (List<String>) -> Result<R>,
): Result<R> {
    val logs =
        globalBuf()
            .toUtf8String()
            .lines()
            .filter { line -> line.contains(" $scope:") }
    return f(logs)
}
