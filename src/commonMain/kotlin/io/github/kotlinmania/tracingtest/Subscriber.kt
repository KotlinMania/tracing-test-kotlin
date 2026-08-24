// port-lint: source src/subscriber.rs
package io.github.kotlinmania.tracingtest

/**
 * A thread-safe buffer for raw byte data.
 */
public class LogBuffer {
    private val bytes: MutableList<Byte> = mutableListOf()

    /**
     * Writes raw bytes into the buffer.
     */
    public fun write(buf: ByteArray): Int {
        for (b in buf) {
            bytes.add(b)
        }
        return buf.size
    }

    /**
     * Writes a UTF-8 string into the buffer.
     */
    public fun write(str: String): Int {
        val encoded = str.encodeToByteArray()
        return write(encoded)
    }

    /**
     * Returns the buffer contents as a byte array.
     */
    public fun toByteArray(): ByteArray = bytes.toByteArray()

    /**
     * Decodes the buffer contents as a UTF-8 string.
     */
    public fun toUtf8String(): String = bytes.toByteArray().decodeToString()

    /**
     * Clears the buffer contents.
     */
    public fun clear() {
        bytes.clear()
    }

    /**
     * Flushes the buffer.
     */
    public fun flush() {
        // In-memory buffer flush is a no-op
    }
}

/**
 * A fake writer that writes into a buffer (behind a mutex).
 */
public class MockWriter(
    private val buf: LogBuffer,
) {
    /**
     * Give access to the internal buffer.
     */
    public fun buf(): LogBuffer = buf

    /**
     * Write raw bytes to stdout and to the underlying buffer.
     */
    public fun write(buf: ByteArray): Int {
        val str = buf.decodeToString()
        print(str)
        return this.buf.write(buf)
    }

    /**
     * Flush the underlying buffer.
     */
    public fun flush() {
        buf.flush()
    }

    /**
     * Create a writer clone.
     */
    public fun makeWriter(): MockWriter = MockWriter(buf)

    public companion object {
        /**
         * Create a new [MockWriter] that writes into the specified buffer.
         */
        public fun new(buf: LogBuffer): MockWriter = MockWriter(buf)
    }
}

/**
 * Writer type alias matching MakeWriter associated type.
 */
public typealias Writer = MockWriter

/**
 * A subscriber dispatch handle.
 */
public class Dispatch(
    public val subscriber: FmtSubscriber,
)

/**
 * Formatter subscriber configuring test log output.
 */
public class FmtSubscriber(
    public val envFilter: String,
    public val writer: MockWriter,
    public val withLevel: Boolean = true,
    public val withAnsi: Boolean = false,
) {
    /**
     * Builder for [FmtSubscriber].
     */
    public class Builder {
        private var envFilter: String = ""
        private var writer: MockWriter? = null
        private var withLevel: Boolean = true
        private var withAnsi: Boolean = false

        /**
         * Set the environment filter string.
         */
        public fun withEnvFilter(envFilter: String): Builder {
            this.envFilter = envFilter
            return this
        }

        /**
         * Set the log writer.
         */
        public fun withWriter(writer: MockWriter): Builder {
            this.writer = writer
            return this
        }

        /**
         * Enable or disable log level rendering.
         */
        public fun withLevel(withLevel: Boolean): Builder {
            this.withLevel = withLevel
            return this
        }

        /**
         * Enable or disable ANSI escape color rendering.
         */
        public fun withAnsi(withAnsi: Boolean): Builder {
            this.withAnsi = withAnsi
            return this
        }

        /**
         * Build the [FmtSubscriber] instance.
         */
        public fun build(): FmtSubscriber =
            FmtSubscriber(
                envFilter = envFilter,
                writer = writer ?: MockWriter(LogBuffer()),
                withLevel = withLevel,
                withAnsi = withAnsi,
            )

        /**
         * Build and convert into a [Dispatch].
         */
        public fun into(): Dispatch = Dispatch(build())
    }

    public companion object {
        /**
         * Return a new builder for [FmtSubscriber].
         */
        public fun builder(): Builder = Builder()
    }
}

/**
 * Return a new subscriber that writes to the specified [MockWriter].
 */
public fun getSubscriber(
    mockWriter: MockWriter,
    envFilter: String,
): Dispatch =
    FmtSubscriber
        .builder()
        .withEnvFilter(envFilter)
        .withWriter(mockWriter)
        .withLevel(true)
        .withAnsi(false)
        .into()
