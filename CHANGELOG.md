# Changelog

All notable changes to this project will be documented in this file.

The format is based on [**Keep a Changelog**](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [**Semantic Versioning**](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

- **[ParameterDefaultsNullInspection]**: added option to include methods that are overridden, but disabled by default;

### Fixed

- **[ParameterDefaultsNullInspection]**: fix wrong "?int" to "?int|null", must be "int|null";

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

