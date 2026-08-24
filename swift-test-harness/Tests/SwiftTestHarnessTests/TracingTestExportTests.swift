import Testing
import TracingTest

@Suite struct TracingTestExportTests {
    @Test func testSwiftModuleLoads() {
        #expect(Bool(true), "TracingTest swift module imported cleanly")
    }

    @Test func testTracedTestScope() {
        let scope = TracedTestScope(scopeName: "swift_test_scope")
        #expect(scope.scopeName == "swift_test_scope")
        #expect(!scope.logsContain(value: "non_existent_log"))
    }
}
