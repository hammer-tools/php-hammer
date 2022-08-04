# Changelog

All notable changes to this project will be documented in this file.

The format is based on [**Keep a Changelog**](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [**Semantic Versioning**](https://semver.org/spec/v2.0.0.html).

## [0.13.1] - 2022-08-04

### Fixed

- This version has been recompiled to ignore already removed files that were unintentionally packaged;

[0.13.1]: https://github.com/hammer-tools/php-hammer/releases/tag/0.13.1

## [0.13.0] - 2022-08-02

### Important

- This version was compiled incorrectly, keeping some deleted files and may fail (fixed in [0.13.1]);

### Added

- **[ReturnTernaryReplacementInspection]**: implemented ("return-ternary can be replaced by if()");
- **[TernarySimplificationInspection]**: implemented ("simplify ternary operation");

[0.13.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.13.0

[0.13.1]: https://github.com/hammer-tools/php-hammer/releases/tag/0.13.1

[ReturnTernaryReplacementInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-return-ternary-replacement-inspection

[TernarySimplificationInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-ternary-simplification-inspection

## [0.12.1] - 2022-08-01

### Changed

- **[DebugFunctionUsageInspection]**: exclude only get_defined_vars() on Blade files;

### Fixed

- Including Sentry as a direct dependency in the built package;

[0.12.1]: https://github.com/hammer-tools/php-hammer/releases/tag/0.12.1

[DebugFunctionUsageInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-debug-function-usage-inspection

## [0.12.0] - 2022-07-31

### Important

- This build may fail while throwing unhandled exceptions due to missing Sentry package as a direct dependency (fixed at [0.12.1]);

### Added

- **[BackslashFilenameUsageInspection]**: implemented ("usage of backslash on filesystem-related names");
- **[FunctionAliasUsageInspection]**: implemented ("usage of alias function");

### Changed

- **[DebugFunctionUsageInspection]**: is now disabled on Blade files to avoid issues with @extends();

### Fixed

- **[SenselessNumberFormatZeroDecimalInspection]**: applicable only if the last argument is an empty string;

[0.12.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.12.0

[0.12.1]: https://github.com/hammer-tools/php-hammer/releases/tag/0.12.1

[BackslashFilenameUsageInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-backslash-filename-usage-inspection

[FunctionAliasUsageInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-function-alias-usage-inspection

[DebugFunctionUsageInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-debug-function-usage-inspection

[SenselessNumberFormatZeroDecimalInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-senseless-number-format-zero-decimal-inspection

## [0.11.1] - 2022-07-30

### Important

- This build may fail while throwing unhandled exceptions due to missing Sentry package as a direct dependency (fixed at [0.12.1]);

### Fixed

- **[FileClassnameCaseInspection]**: namespaced files were not being inspected;

[0.11.1]: https://github.com/hammer-tools/php-hammer/releases/tag/0.11.1

[0.12.1]: https://github.com/hammer-tools/php-hammer/releases/tag/0.12.1

[FileClassnameCaseInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-file-classname-case-inspection

## [0.11.0] - 2022-07-29

### Added

- **PhpStorm 2022.2** is now supported;
- **[ClassnameLiteralInspection]**: implemented ("class name as string can be replaced");

[0.11.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.11.0

[ClassnameLiteralInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-classname-literal-inspection

## [0.10.0] - 2022-07-21

### Added

- **[NativeMemberUsageInspection]**: implemented ("using native types as objects");

### Fixed

- **[DebugFunctionUsageInspection]**: improved identification of helper functions such as dd();

[0.10.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.10.0

[NativeMemberUsageInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-native-member-usage-inspection

[DebugFunctionUsageInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-debug-function-usage-inspection

## [0.9.0] - 2022-07-19

### Added

- **[CompactArgumentInvalidInspection]**: implemented ("invalid compact() argument");
- **[CompactDuplicatedTermsInspection]**: implemented ("duplicated terms in compact()");
- **[CompactVariableInspection]**: implemented ("variable usage in compact()");
- **[DebugFunctionUsageInspection]**: implemented ("debug-related function usage");

[0.9.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.9.0

[CompactArgumentInvalidInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-compact-argument-invalid-inspection

[CompactDuplicatedTermsInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-compact-duplicated-terms-inspection

[CompactVariableInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-compact-variable-inspection

[DebugFunctionUsageInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-debug-function-usage-inspection

## [0.8.0] - 2022-07-18

### Added

- **[IfSimplificationAndInspection]**: implemented ("merge simple nested conditionals");
- **[IfSimplificationElseInspection]**: implemented ("drop useless conditional");
- **[IfSimplificationOrInspection]**: implemented ("merge simple subsequent conditionals");
- **[NullCheckFormatInspection]**: implemented ("null check format");
- **[SenselessNumberFormatZeroDecimalInspection]**: implemented ("senseless number_format() with zero decimal point");
- **[StringCurlyInspection]**: implemented ("variable must have curly braces");

### Changed

- **[RedundantReturnPointInspection]**: now it understand situations that include comments;
- **[StringSimplificationInspection]**: quick-fix does not needs add type casting when applied to array key;

### Fixed

- **[StringSimplificationInspection]**: fix normalization for deprecated ${x} variable format;

[0.8.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.8.0

[IfSimplificationAndInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-if-simplification-and-inspection

[IfSimplificationElseInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-if-simplification-else-inspection

[IfSimplificationOrInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-if-simplification-or-inspection

[NullCheckFormatInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-null-check-format-inspection

[SenselessNumberFormatZeroDecimalInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-senseless-number-format-zero-decimal-inspection

[StringCurlyInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-string-curly-inspection

[RedundantReturnPointInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-redundant-return-point-inspection

[StringSimplificationInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-string-simplification-inspection

## [0.7.0] - 2022-07-13

### Added

- **[ArrayMapFirstClassInspection]**: implemented ("first-class callable replacement for array_map()");
- **[BacktickReplacementInspection]**: implemented ("replace backtick operator");
- **[FunctionErrorSilencedInspection]**: implemented ("function call using error control operator");
- **[SenselessArrayMergeUsageInspection]**: implemented ("senseless array_merge() usage");
- **[SenselessParentCallEmptyInspection]**: implemented ("senseless call to empty parent method");
- **[UnaryOperatorFormatInspection]**: implemented ("unary increase/decrease format");

### Changed

- **[FileClassnameCaseInspection]**: added option to allow checking non-root classes from file, disabled by default;
- **[FileClassnameCaseInspection]**: added the option to allow the inspection to check files with more than one class definition, disabled by default;
- **[FileClassnameCaseInspection]**: added an option that allows to ignore classes that belong to files that would become invalid identifiers, disabled by default;
- **[ParameterDefaultsNullInspection]**: added an option to allow inspection to work on referenced parameters, disabled by default;

### Fixed

- **[FileClassnameCaseInspection]**: files starting with numbers can throw exceptions to quick-fix;

[0.7.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.7.0

[ArrayMapFirstClassInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-array-map-first-class-inspection

[BacktickReplacementInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-backtick-replacement-inspection

[FunctionErrorSilencedInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-function-error-silenced-inspection

[SenselessArrayMergeUsageInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-senseless-array-merge-usage-inspection

[SenselessParentCallEmptyInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-senseless-parent-call-empty-inspection

[UnaryOperatorFormatInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-unary-operator-format-inspection

[FileClassnameCaseInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-file-classname-case-inspection

[ParameterDefaultsNullInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-parameter-defaults-null-inspection

## [0.6.0] - 2022-07-12

### Added

- **[FileClassnameCaseInspection]**: implemented ("class name must match the file that stores it");

### Fixed

- **[CompactReplacementInspection]**: ignore arrays that have variables with references;

[0.6.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.6.0

[FileClassnameCaseInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-file-classname-case-inspection

[CompactReplacementInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-compact-replacement-inspection

## [0.5.1] - 2022-07-12

### Changed

- **[ParameterDefaultsNullInspection]**: added option to include methods that are overridden, but disabled by default;
- **[ParameterDefaultsNullInspection]**: added an option to disable inspection on untyped or nullable parameters as it may cause behavior change, disabled by default;

### Fixed

- **[ParameterDefaultsNullInspection]**: fix wrong "?int" to "?int|null", must be "int|null";

[0.5.1]: https://github.com/hammer-tools/php-hammer/releases/tag/0.5.1

[ParameterDefaultsNullInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-parameter-defaults-null-inspection

## [0.5.0] - 2022-07-12

### Added

- **[DollarSignOutsideCurlyBracesInspection]**: implemented ("dollar sign outside of the curly braces");

### Fixed

- **[StringSimplificationInspection]**: fix for "{$x->from->y()}" case;

[0.5.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.5.0

[DollarSignOutsideCurlyBracesInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-dollar-sign-outside-curly-braces-inspection

[StringSimplificationInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-string-simplification-inspection

## [0.4.0] - 2022-07-11

### Added

- **[ClassSelfReferenceFormatInspection]**: implemented ("class self-reference format normalization");
- **[CompactReplacementInspection]**: implemented ("replace with compact()");

[0.4.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.4.0

[ClassSelfReferenceFormatInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-class-self-reference-format-inspection

[CompactReplacementInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-compact-replacement-inspection

## [0.3.0] - 2022-07-10

### Added

- **[ComparisonScalarOrderInspection]**: implemented ("comparison order with scalar normalization");
- **[StringSimplificationInspection]**: implemented ("string can be simplified");
- **[ToStringSimplificationInspection]**: implemented ("call to __toString() can be simplified");
- **[TypeCastNormalizationInspection]**: implemented ("type cast normalization");

[0.3.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.3.0

[ComparisonScalarOrderInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-comparison-scalar-order-inspection

[StringSimplificationInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-string-simplification-inspection

[ToStringSimplificationInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-to-string-simplification-inspection

[TypeCastNormalizationInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-type-cast-normalization-inspection

## [0.2.0] - 2022-07-07

### Added

- **[AnonymousFunctionStaticInspection]**: implemented ("anonymous function can be static");
- **[CastIntvalInspection]**: implemented ("replace function cast to type cast");
- **[ParameterDefaultsNullInspection]**: implemented ("parameter defaults to null");
- **[ParameterImplicitlyNullableInspection]**: implemented ("parameter is implicitly nullable");
- **[StaticAnonymousFunctionCannotAccessThisInspection]**: implemented ("static anonymous function can't access $this");
- **[UnusedUseVariableInspection]**: implemented ("unused use() variable");
- **[UnusedUseVariableReferenceInspection]**: implemented ("unused use() reference for variable");

### Fixed

- **[SortUseVariablesInspection]**: fix `use()` sort with not used variables, keeping original order as exception;

[0.2.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.2.0

[AnonymousFunctionStaticInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-anonymous-function-static-inspection

[CastIntvalInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-cast-intval-inspection

[ParameterDefaultsNullInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-parameter-defaults-null-inspection

[ParameterImplicitlyNullableInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-parameter-implicitly-nullable-inspection

[StaticAnonymousFunctionCannotAccessThisInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-static-anonymous-function-cannot-access-this-inspection

[UnusedUseVariableInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-unused-use-variable-inspection

[UnusedUseVariableReferenceInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-unused-use-variable-reference-inspection

[SortUseVariablesInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-sort-use-variables-inspection

## [0.1.0] - 2022-07-02

### Added

- **[ArrayPackableInspection]**: implemented ("array can be packed");
- **[CaseSeparatorFormatInspection]**: implemented ("case separator format");
- **[NullableTypeFormatInspection]**: implemented ("nullable types format");
- **[NullableTypeRightmostInspection]**: implemented ("nullable type on the rightmost side");
- **[RedundantReturnPointInspection]**: implemented ("redundant return point");
- **[SortUseVariablesInspection]**: implemented ("sort use() variables");

[0.1.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.1.0

[ArrayPackableInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-array-packable-inspection

[CaseSeparatorFormatInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-case-separator-format-inspection

[NullableTypeFormatInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-nullable-type-format-inspection

[NullableTypeRightmostInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-nullable-type-rightmost-inspection

[RedundantReturnPointInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-redundant-return-point-inspection

[SortUseVariablesInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-sort-use-variables-inspection

