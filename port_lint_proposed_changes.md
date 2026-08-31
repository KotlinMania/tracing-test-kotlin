# port-lint Proposed Changes

**Generated:** 2026-08-31
**Source:** tmp/tracing-test/src
**Target:** src/commonMain/kotlin/io/github/kotlinmania/tracingtest

These are review proposals only. They are emitted when a Rust -> Kotlin pair matches only after fallback normalization, so the existing `port-lint` header is not an exact provenance match.

| Target file | Current header | Proposed header | Source path | Reason |
|-------------|----------------|-----------------|-------------|--------|
| `src/commonMain/kotlin/io/github/kotlinmania/tracingtest/Subscriber.kt` | `// port-lint: source tracing-test/src/subscriber.rs` | `// port-lint: source subscriber.rs` | `subscriber.rs` | `port-lint provenance header matched only after fallback normalization: 'tracing-test/src/subscriber.rs' vs expected 'subscriber.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/tracingtest/SubscriberTest.kt` | `// port-lint: tests tracing-test/src/subscriber.rs` | `// port-lint: tests subscriber.rs` | `subscriber.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:tracing-test/src/subscriber.rs' vs expected 'subscriber.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/tracingtest/Internal.kt` | `// port-lint: source tracing-test/src/internal.rs` | `// port-lint: source internal.rs` | `internal.rs` | `port-lint provenance header matched only after fallback normalization: 'tracing-test/src/internal.rs' vs expected 'internal.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/tracingtest/InternalTest.kt` | `// port-lint: tests tracing-test/src/internal.rs` | `// port-lint: tests internal.rs` | `internal.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:tracing-test/src/internal.rs' vs expected 'internal.rs'` |
