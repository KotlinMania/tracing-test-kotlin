// port-lint: tests subscriber.rs
package io.github.kotlinmania.tracingtest

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class SubscriberTest {
    @Test
    fun testLogBufferOperations() {
        val buffer = LogBuffer()
        assertEquals(0, buffer.toByteArray().size)
        assertEquals("", buffer.toUtf8String())

        val writtenBytes = buffer.write("hello".encodeToByteArray())
        assertEquals(5, writtenBytes)
        assertEquals("hello", buffer.toUtf8String())

        val writtenStr = buffer.write(" world")
        assertEquals(6, writtenStr)
        assertEquals("hello world", buffer.toUtf8String())

        buffer.flush()
        buffer.clear()
        assertEquals(0, buffer.toByteArray().size)
    }

    @Test
    fun testMockWriterWritesToBuffer() {
        val buffer = LogBuffer()
        val writer = MockWriter.new(buffer)
        assertEquals(buffer, writer.buf())

        val count = writer.write("test log output\n".encodeToByteArray())
        assertEquals(16, count)
        assertTrue(buffer.toUtf8String().contains("test log output"))

        writer.flush()
        val clonedWriter = writer.makeWriter()
        assertNotNull(clonedWriter)
        assertEquals(buffer, clonedWriter.buf())
    }

    @Test
    fun testFmtSubscriberBuilderAndGetSubscriber() {
        val buffer = LogBuffer()
        val writer = MockWriter.new(buffer)

        val subscriber =
            FmtSubscriber
                .builder()
                .withEnvFilter("my_crate=trace")
                .withWriter(writer)
                .withLevel(true)
                .withAnsi(false)
                .build()

        assertEquals("my_crate=trace", subscriber.envFilter)
        assertEquals(writer, subscriber.writer)
        assertTrue(subscriber.withLevel)
        assertEquals(false, subscriber.withAnsi)

        val dispatch = getSubscriber(writer, "my_crate=info")
        assertNotNull(dispatch.subscriber)
        assertEquals("my_crate=info", dispatch.subscriber.envFilter)
    }
}
