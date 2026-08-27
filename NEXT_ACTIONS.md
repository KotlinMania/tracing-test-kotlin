# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 3/3 (100.0%)
- **Function parity:** 9/9 matched (target 40) — 100.0%
- **Class/type parity:** 2/2 matched (target 12) — 100.0%
- **Combined symbol parity:** 11/11 matched (target 52) — 100.0%
- **Average inline-code cosine:** 0.69 (function body across 2 matched files)
- **Average documentation cosine:** 0.65 (doc text across 2 matched files)
- **Cheat-zeroed Files:** 1
- **Critical Issues:** 1 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. tracing-test.subscriber

- **Target:** `tracingtest.Subscriber`
- **Similarity:** 0.74
- **Dependents:** 0
- **Priority Score:** 802.6
- **Functions:** 6/6 matched (target 22)
- **Missing functions:** _none_
- **Types:** 2/2 matched (target 7)
- **Missing types:** _none_

### 2. tracing-test.internal

- **Target:** `tracingtest.Internal`
- **Similarity:** 0.65
- **Dependents:** 0
- **Priority Score:** 303.5
- **Functions:** 3/3 matched (target 10)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_

### 3. tracing-test.lib

- **Target:** `tracingtest.Lib [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched (target 8)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 3)
- **Missing types:** _none_

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

