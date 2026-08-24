# port-lint Proposed Changes

**Generated:** 2026-08-24
**Source:** tmp/tracing-test
**Target:** src

These are review proposals only. They are emitted when a Rust -> Kotlin pair matches only after fallback normalization, so the existing `port-lint` header is not an exact provenance match.

| Target file | Current header | Proposed header | Source path | Reason |
|-------------|----------------|-----------------|-------------|--------|
| `commonMain/kotlin/io/github/kotlinmania/tracingtest/Subscriber.kt` | `// port-lint: source subscriber.rs` | `// port-lint: source subscriber.rs` | `subscriber.rs` | `port-lint provenance header matched only after fallback normalization: 'subscriber.rs' vs expected 'subscriber.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/tracingtest/Internal.kt` | `// port-lint: source internal.rs` | `// port-lint: source internal.rs` | `internal.rs` | `port-lint provenance header matched only after fallback normalization: 'internal.rs' vs expected 'internal.rs'` |
