# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 2/3 (66.7%)
- **Function parity:** 9/9 matched (target 25) — 100.0%
- **Class/type parity:** 2/2 matched (target 7) — 100.0%
- **Combined symbol parity:** 11/11 matched (target 32) — 100.0%
- **Average inline-code cosine:** 0.69 (function body across 2 matched files)
- **Average documentation cosine:** 0.65 (doc text across 2 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 0 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. subscriber

- **Target:** `tracingtest.Subscriber`
- **Similarity:** 0.74
- **Dependents:** 0
- **Priority Score:** 802.6
- **Functions:** 6/6 matched (target 19)
- **Missing functions:** _none_
- **Types:** 2/2 matched (target 6)
- **Missing types:** _none_

### 2. internal

- **Target:** `tracingtest.Internal`
- **Similarity:** 0.65
- **Dependents:** 0
- **Priority Score:** 303.5
- **Functions:** 3/3 matched (target 6)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `lib` | `Lib` | 0 | `src/lib.rs` | `Lib.kt` |

