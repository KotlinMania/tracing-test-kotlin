import Testing
import TracingTest

@Suite struct TracingTestExportTests {
    @Test func testSwiftModuleLoads() {
        #expect(Bool(true), "TracingTest swift module imported cleanly")
    }
}
