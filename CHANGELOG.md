# Changelog

All notable changes to this project will be documented in this file.

The format is based on [**Keep a Changelog**](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [**Semantic Versioning**](https://semver.org/spec/v2.0.0.html).

## [0.33.0] - 2025-04-17

### Added

- **PhpStorm 2025.1** is now supported and minimum supported version due to API changes;

[0.33.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.33.0

## [0.32.0] - 2024-12-16

### Added

- **PhpStorm 2024.3** is now supported and minimum supported version due to API changes;

[0.32.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.32.0

## [0.31.0] - 2024-08-15

### Added

- **PhpStorm 2024.2** (EAP) is now supported and minimum supported version due to API changes;

[0.31.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.31.0

## [0.30.0] - 2024-02-11

### Added

- **PhpStorm 2024.1** (EAP) is now supported and minimum supported version due to API changes;

[0.30.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.30.0

## [0.29.2] - 2023-11-09

### Fixed

- **[SenselessParentCallEmptyInspection]**: in certain cases, using `trait` with `@mixin` could trick the analysis into believing that the method declared in the mixin was closer than the actually related method. Fixes #49;

[0.29.2]: https://github.com/hammer-tools/php-hammer/releases/tag/0.29.2

[SenselessParentCallEmptyInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-senseless-parent-call-empty-inspection

## [0.29.1] - 2023-11-08

### Fixed

- **[MissingParentCallInspection]**: the call to `parent::call()` will not be required if the parent method has only an exception (`throw`). Fixes #50;
- **[OverrideMissingInspection]**: methods defined by unused `trait` should not expect `#[Override]`. Related to #44;
- **[OverrideMissingInspection]**, **[OverrideIllegalInspection]**: methods specifically defined by phpdoc should be ignored since they are not real overrides. Related to #50;

[0.29.1]: https://github.com/hammer-tools/php-hammer/releases/tag/0.29.1

[MissingParentCallInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-missing-parent-call-inspection

[OverrideMissingInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-override-missing-inspection

[OverrideIllegalInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-override-illegal-inspection

## [0.29.0] - 2023-11-07

### Added

- **[DangerousExtractInspection]**: this inspection checks for the dangerous usage of the extract() function. Fixes #47;
- **[OverrideIllegalInspection]**: included two searches related to the issue of potentially illegal use of `#[Override]` in traits. Related to #44;
- **[OverrideMissingInspection]**: reintroduced the possibility of suggesting the `#[Override]` attribute for `trait`, as long as the method is overridden in all classes that use this trait itself. Related to #39 and #44;
- **[ParameterDefaultsNullInspection]**: added support for de-promoting `readonly` parameters, allowing this inspection to do the complete job (disabled by default). Fixes #35;
- **[UselessIsComparisonInspection]**: new inspection that will check for the use of useless comparison methods, such as `is_int($x = 5)`. Fixes #43;
- **[UselessIsComparisonInspection]**: added quick-fix;

### Changed

- **[OverrideIllegalInspection]**: if an unused `trait` defines the `#[Override]` attribute for a method, for now, the issue will be ignored until its first use (configurable via options). Fixes #44;
- **[OverrideIllegalInspection]**: if a method from a `trait` is indeed overriding another method, but in another class that uses the trait this method doesn't exist, then that is an error. Fixes #44;

### Fixed

- **[OverrideIllegalInspection]**: methods using `#[Override]` in `trait` classes were falsely reporting issues, even when the classes that used them indeed had the methods specified as overridden. Fixes #44;
- **[OverrideIllegalInspection]**: ignore `private` methods when determining whether `#[Override]` should be applied. Fixes #44;
- **[OverrideMissingInspection]**: don't suggest the `#[Override]` attribute when the parent method is `private`. Fixes #44;

[0.29.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.29.0

[DangerousExtractInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-dangerous-extract-inspection

[OverrideIllegalInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-override-illegal-inspection

[OverrideMissingInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-override-missing-inspection

[ParameterDefaultsNullInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-parameter-defaults-null-inspection

[UselessIsComparisonInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-useless-is-comparison-inspection

## [0.28.2] - 2023-10-23

### Changed

- **[OverrideMissingInspection]**: added an option to allow this inspection to work before version 8.3; Fixes #39;
- **[SensitiveParameterInspection]**: added an option to allow this inspection to work before version 8.2; Fixes #39;

[0.28.2]: https://github.com/hammer-tools/php-hammer/releases/tag/0.28.2

[OverrideMissingInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-override-missing-inspection

[SensitiveParameterInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-sensitive-parameter-inspection

## [0.28.1] - 2023-10-23

### Changed

- **[OverrideMissingInspection]**: restricted to work only with PHP 8.3+ projects. Fixes #39;
- **[SensitiveParameterInspection]**: restricted to work only with PHP 8.2+ projects. Fixes #39;

[0.28.1]: https://github.com/hammer-tools/php-hammer/releases/tag/0.28.1

[OverrideMissingInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-override-missing-inspection

[SensitiveParameterInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-sensitive-parameter-inspection

## [0.28.0] - 2023-10-21

### Added

- **[ComparisonScalarOrderInspection]**: added a new option that allows moving assignments to the left when scalars on the right are preferred (disabled by default). Fixes #40;
- **[OverrideMissingInspection]**: added an option that allows a `parent::call()` to be a replacement for the `#[Override]` attribute. Disabled by default. Fixes #39;
- **[SensitiveParameterInspection]**: check the use of sensitive words as function and method parameter names, suggesting the use of the `#[SensitiveParameter]` attribute. Fixes #41;

[0.28.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.28.0

[ComparisonScalarOrderInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-comparison-scalar-order-inspection

[OverrideMissingInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-override-missing-inspection

[SensitiveParameterInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-sensitive-parameter-inspection

## [0.27.0] - 2023-10-20

### Added

- **[MissingParentCallInspection]**: added a new option that makes the inspection work even if the `#[Override]` attribute is declared on the method. Fixes #39;
- **[OverrideIllegalInspection]**: new inspection that checks for the illegal use of the `#[Override]` attribute and recommends its removal to prevent fatal errors in PHP 8.3+. Fixes #39;
- **[OverrideMissingInspection]**: new inspection that checks if the `#[Override]` attribute has been declared in methods overriding parent methods. Fixes #39;

### Fixed

- **[ComparisonScalarOrderInspection]**: ignores the condition where the right side of the operation is an assignment that, when swapped with the left side, changes the code's behavior;

[0.27.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.27.0

[MissingParentCallInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-missing-parent-call-inspection

[OverrideIllegalInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-override-illegal-inspection

[OverrideMissingInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-override-missing-inspection

[ComparisonScalarOrderInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-comparison-scalar-order-inspection

## [0.26.0] - 2023-10-18

### Added

- **[CompactReplacementInspection]**: added a new option that allows to choose the format of the arguments generated for the `compact()` function, either _positional_ or _named_. Fixes #36;

### Fixed

- **[Inspection]**: added hammer emoji to all quick-fixes;
- **[ParameterDefaultsNullInspection]**, **[SortUseVariablesInspection]**, **[UnusedUseVariableInspection]**: when a quick-fix preview was generated, an exception was thrown. Fixes #37;

[0.26.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.26.0

[CompactReplacementInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-compact-replacement-inspection

[Inspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-inspection

[ParameterDefaultsNullInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-parameter-defaults-null-inspection

[SortUseVariablesInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-sort-use-variables-inspection

[UnusedUseVariableInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-unused-use-variable-inspection

## [0.25.1] - 2023-10-17

### Fixed

- **[MissingParentCallInspection]**: do not suggest `parent` call if there is no method with the same name in the parent classes. Fixes #18;

[0.25.1]: https://github.com/hammer-tools/php-hammer/releases/tag/0.25.1

[MissingParentCallInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-missing-parent-call-inspection

## [0.25.0] - 2023-10-16

### Added

- **[MissingParentCallInspection]**: will now consider renamed methods from traits as well;

### Fixed

- **[AnonymousFunctionStaticInspection]**: scope function cannot be set as `static` if it accesses non-static methods in a static way. Fixes #25;
- **[AnonymousFunctionStaticInspection]**: when making a function static under specific conditions, the code formatting didn't create a space between `static` and `function`, as expected, resulting in a code error. Fixes #29;
- **[ParameterDefaultsNullInspection]**: ignore promoted properties that use `readonly` modifier since it's impossible to modify them within the function body, even inside the constructor. Fixes #33;
- **[ParameterDefaultsNullInspection]**: assign to `$this->{property name}` when it's a promoted property - otherwise, there will be incorrect behavior after applying the quick-fix. Fixes #32;

[0.25.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.25.0

[MissingParentCallInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-missing-parent-call-inspection

[AnonymousFunctionStaticInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-anonymous-function-static-inspection

[ParameterDefaultsNullInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-parameter-defaults-null-inspection

## [0.24.0] - 2023-10-02

### Changed

- **[FunctionSpreadingInspection]**: PHP spread works best for PHP 8.1+, so this inspection will work in a limited way to avoid false positives; fixes #24;

### Fixed

- **[FunctionSpreadingInspection]**: correctly validate the spread of functions - they need to be executed (`$ex()`), and not just "mentioned" (`$ex`);
- **[NullableTypeFormatInspection]**: intersecting types should be ignored, as they do not support short format (eg. `?(A&B)`); fixes #28;
- **[ParameterImplicitlyNullableInspection]**: intersecting types are more restrictive and will only work for the "long" format (eg. `(A&B)|null`); fixes/related to #28;
- **[StaticAnonymousFunctionCannotAccessThisInspection]**: check that the `function` that uses `$this` is `static` up to one level, so that nested structures can work as expected. Fixes #21;

[0.24.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.24.0

[FunctionSpreadingInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-function-spreading-inspection

[NullableTypeFormatInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-nullable-type-format-inspection

[ParameterImplicitlyNullableInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-parameter-implicitly-nullable-inspection

[StaticAnonymousFunctionCannotAccessThisInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-static-anonymous-function-cannot-access-this-inspection

## [0.23.0] - 2023-08-10

### Added

- **[ParameterImplicitlyNullableInspection]**: added option to specify the replacement nullable format between short and long (as default) (fixes #19);

### Changed

- **[ParameterImplicitlyNullableInspection]**: fallback nullable type format to short format automatically for PHP version lower than 8.0 (fixes #19);

### Fixed

- **[ClassSelfReferenceFormatInspection]**: ignore inspection on traits (fixes #20);

[0.23.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.23.0

[ParameterImplicitlyNullableInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-parameter-implicitly-nullable-inspection

[ClassSelfReferenceFormatInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-class-self-reference-format-inspection

## [0.22.2] - 2023-05-26

### Fixed

- **Additional feature**: fix checkbox control to not be disabled, but unselected;

[0.22.2]: https://github.com/hammer-tools/php-hammer/releases/tag/0.22.2

## [0.22.1] - 2023-05-21

### Fixed

- **Additional feature**: add missing semicolon on save PHP documents (set as disabled for default);

[0.22.1]: https://github.com/hammer-tools/php-hammer/releases/tag/0.22.1

## [0.22.0] - 2023-05-21

### Added

- **Additional feature**: add missing semicolon on save PHP documents;
- **PhpStorm 2023.2** (EAP) is now supported and minimum supported version due to API changes;

[0.22.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.22.0

## [0.21.1] - 2023-05-21

### Added

- **Exceptional release:** new features support for PhpStorm 2023.1;
- **[ObsoleteFunctionInspection]**: implemented ("obsolete function usage") (fixes #9);
- **[StrictComparisonInspection]**: implemented ("strict comparison usage") (fixes #8);

### Changed

- **[AnonymousFunctionStaticInspection]**: disable this inspection for Blade files (fixes #14);

### Fixed

- **[DebugFunctionUsageInspection]**: skip print_r() inside Blade to avoid issues with `@entangle()` directive (fixes #13);
- **[NativeMemberUsageInspection]**: allow variables of type class-string to be used as object (fixes #12);

[0.21.1]: https://github.com/hammer-tools/php-hammer/releases/tag/0.21.1

[ObsoleteFunctionInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-obsolete-function-inspection

[StrictComparisonInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-strict-comparison-inspection

[AnonymousFunctionStaticInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-anonymous-function-static-inspection

[DebugFunctionUsageInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-debug-function-usage-inspection

[NativeMemberUsageInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-native-member-usage-inspection

## [0.21.0] - 2023-02-01

### Added

- **[FunctionSpreadingInspection]**: implemented ("function to spread");

### Fixed

- **[SenselessArrayUnpackingInspection]**: this inspection cannot be applied to Generator functions;

[0.21.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.21.0

[FunctionSpreadingInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-function-spreading-inspection

[SenselessArrayUnpackingInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-senseless-array-unpacking-inspection

## [0.20.0] - 2023-01-27

### Added

- **[MissingParentCallInspection]**: implemented ("missing parent::call()");

### Fixed

- **[SenselessParentCallEmptyInspection]**: ignore empty methods but with comments inside;

[0.20.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.20.0

[MissingParentCallInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-missing-parent-call-inspection

[SenselessParentCallEmptyInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-senseless-parent-call-empty-inspection

## [0.19.2] - 2023-01-26

### Added

- **PhpStorm 2023.1** (EAP) is now supported;

[0.19.2]: https://github.com/hammer-tools/php-hammer/releases/tag/0.19.2

## [0.19.1] - 2023-01-25

### Fixed

- **[ParameterDefaultsNullInspection]**: functions without parameters were having problem;

[0.19.1]: https://github.com/hammer-tools/php-hammer/releases/tag/0.19.1

[ParameterDefaultsNullInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-parameter-defaults-null-inspection

## [0.19.0] - 2023-01-24

### Important

- This plugin is now entirely free;

### Added

- **[ClassnameLiteralInspection]**: added an option to include functions like `class_exists()` and `class_alias()` (enabled by default);
- **[ClassnameLiteralInspection]**: added an option to include non-existent classes to the analysis (disabled by default);
- **[ParameterDefaultsNullInspection]**: added an option to include the latest parameter to the analysis (disabled by default);
- **[ParameterDefaultsNullInspection]**: added an option to include booleans to the analysis (enabled by default);

[0.19.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.19.0

[ClassnameLiteralInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-classname-literal-inspection

[ParameterDefaultsNullInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-parameter-defaults-null-inspection

## [0.18.0] - 2022-11-02

### Added

- **[FrameworkOptionalReplacementInspection]**: implemented ("replace optional() function");

### Fixed

- **[ClassSelfReferenceFormatInspection]**: fix rare assertion exception;
- **[DebugFunctionUsageInspection]**: fix rare exception;
- **[FileClassnameCaseInspection]**: fix rare assertion exception;

[0.18.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.18.0

[FrameworkOptionalReplacementInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-framework-optional-replacement-inspection

[ClassSelfReferenceFormatInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-class-self-reference-format-inspection

[DebugFunctionUsageInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-debug-function-usage-inspection

[FileClassnameCaseInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-file-classname-case-inspection

## [0.17.0] - 2022-10-20

### Added

- **[CompactInsideShortFunctionInspection]**: implemented ("usage of compact() inside short functions");

### Changed

- **[CompactReplacementInspection]**: ignore suggestion inside short arrow functions due to a PHP bug (#78970);

[0.17.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.17.0

[CompactInsideShortFunctionInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-compact-inside-short-function-inspection

[CompactReplacementInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-compact-replacement-inspection

## [0.16.0] - 2022-09-26

### Changed

- Minimum supported version raised to 2022.3 due to API changes (EAP 223);
- **[SortUseVariablesInspection]**: use SmartPointer instead of direct PsiElement;

[0.16.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.16.0

[SortUseVariablesInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-sort-use-variables-inspection

## [0.15.0] - 2022-08-18

### Added

- **[SenselessArrayUnpackingInspection]**: implemented ("array unpacking can be simplified");

### Changed

- **[IfSimplificationAndInspection]**: disabled in Blade files (due WI-68279);

### Fixed

- **[ConditionalsNullSafeOperatorInspection]**: must skip if used as part of another expression (eg. comparisons);

[0.15.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.15.0

[SenselessArrayUnpackingInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-senseless-array-unpacking-inspection

[IfSimplificationAndInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-if-simplification-and-inspection

[ConditionalsNullSafeOperatorInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-conditionals-null-safe-operator-inspection

## [0.14.0] - 2022-08-11

### Added

- **[ConditionalsNullSafeOperatorInspection]**: implemented ("replace with null-safe operator");

[0.14.0]: https://github.com/hammer-tools/php-hammer/releases/tag/0.14.0

[ConditionalsNullSafeOperatorInspection]: https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-conditionals-null-safe-operator-inspection

## [0.13.2] - 2022-08-05

### Important

- The purpose of this release is to make it compatible with **Laravel Hammer** plugin;

[0.13.2]: https://github.com/hammer-tools/php-hammer/releases/tag/0.13.2

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

### Important

- First public release;

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

