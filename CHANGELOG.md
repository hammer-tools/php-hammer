# Changelog

All notable changes to this project will be documented in this file.

The format is based on [**Keep a Changelog**](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [**Semantic Versioning**](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Fixed

- **SortUseVariablesInspection:** fix `use()` sort with not used variables, keeping original order as exception;

## [0.1.0 EAP #1] - 2022-07-02

### Added

- Initial release;
- **NullableTypeFormatInspection:** implemented ("nullable type format");
- **NullableTypeRightmostInspection:** implemented ("nullable type on the rightmost side");
- **ArrayPackableInspection:** implemented ("array can be packed");
- **CaseSeparatorFormatInspection:** implemented ("case separator format");
- **SortUseVariablesInspection:** implemented ("sort use() variables");
- **RedundantReturnPointInspection:** implemented ("redundant return point");
