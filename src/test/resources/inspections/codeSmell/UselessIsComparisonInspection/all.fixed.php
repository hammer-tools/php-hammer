<?php

class DummyProperties {
    const X_CONSTANT = 123;

    public int $dummyInt = 123;

    /** @var int */
    public $dummyConstantNullable = DummyProperties::X_CONSTANT;

    /** @var bool */
    public $dummyBoolNullable;
    public $dummyStringNullable = 'string';
    public float $dummyFloatNull;
    public float|null $dummyFloatNullable;

    /**
     * @param string $key
     * @param array<string, mixed> $attributes
     */
    function get(string $key, array $attributes) {}
}

// Report: common cases.

(function () {
    $dummyInt = 123;

    // Always scalar:

    // Always integer:
    true === true;
    true !== true;

    // Never float:

    // Complex quick-fixes:
    if (true) {
        assert(true);
        assert(false);

        return false;
    }
})();

(function () {
    $dummyFloat = 123.45;

    // Always scalar:

    // Always float:

    // Never integer:
})();

(function () {
    $dummyString = 'dummy';

    // Always scalar:

    // Always string:

    // Never integer:
})();

(function () {
    $dummyBooleanTrue = true;

    // Always scalar:

    // Always boolean:

    // Never integer:
})();

(function () {
    $dummyBooleanFalse = false;

    // Always scalar:

    // Always boolean:

    // Never integer:
})();

(function () {
    $dummyNull = null;

    // Always null:

    // Never integer:

    // Never scalar:
})();

(function () {
    $dummyObject = new SplFileInfo();

    // Always object:

    // Never integer:

    // Never scalar:
})();

// Report: return used as argument.

(function () {
    // Always integer:

    // Never integer:
})();

(function () {
    $instance = new DummyProperties;

    // Always integer:

    // Never float:

    // Never string:
})();

(function () {
    /** @var int|null $dummyIntNullable */
    $dummyIntNullable = 123;

    // Never float:
})();

(function () {
    $dummyResourceOrFalse = getResourceOrFalse();

    // Never integer:
})();

// Skip: common cases.

(function () {
    /** @var int|null $dummyIntNullable */
    $dummyIntNullable = 123;

    is_scalar($dummyIntNullable); // Skip: still could be <int>.
    is_null($dummyIntNullable); // Skip: still could be <int>.
    is_int($dummyIntNullable); // Skip: still could be <null>.
    is_integer($dummyIntNullable); // Skip: still could be <null>.
    is_long($dummyIntNullable); // Skip: still could be <null>.

    is_unknown_function($dummyIntNullable); // Skip: unknown checker function.
})();

(function () {
    $dummyResourceOrFalse = getResourceOrFalse();

    is_bool($dummyResourceOrFalse); // Skip: still could be <resource>.
    is_scalar($dummyResourceOrFalse); // Skip: still could be <resource> (matches <bool> here too).
})();

(function () {
    /** @var DummyProperties|string|int $dummyMixedObject */
    is_object($dummyMixedObject); // Skip: still could be <string>, <int>.
})();

// Skip: mixed or unknown types.

(function () {
    /** @var mixed $dummyMixed */
    $dummyMixed = 123;

    is_scalar($dummyMixed);
    is_int($dummyMixed);
    is_integer($dummyMixed);
    is_long($dummyMixed);
    is_float($dummyMixed);
})();

(function ($dummyUnknown) {
    is_scalar($dummyUnknown);
    is_int($dummyUnknown);
    is_integer($dummyUnknown);
    is_long($dummyUnknown);
    is_float($dummyUnknown);
    is_object($dummyUnknown);
})();

// Skip: property still could be <null>.

(function () {
    is_string((new DummyProperties)->dummyStringNullable); // Skip: property still could be <null>.
    is_int((new DummyProperties)->dummyConstantNullable); // Skip: property still could be <null>.
    is_bool((new DummyProperties)->dummyBoolNullable); // Skip: property still could be <null>.
    is_float((new DummyProperties)->dummyFloatNull); // Skip: property still could be <null> (initially).
    is_float((new DummyProperties)->dummyFloatNullable); // Skip: property still could be <null>.

    $instance = new DummyProperties;

    is_string($instance->dummyStringNullable); // Skip: property still could be <null>.
    is_int($instance->dummyConstantNullable); // Skip: property still could be <null>.
    is_bool($instance->dummyBoolNullable); // Skip: property still could be <null>.
    is_float($instance->dummyFloatNull); // Skip: property still could be <null> (initially).
    is_float($instance->dummyFloatNullable); // Skip: property still could be <null>.
})();

// Skip: unknown key-value type.

new class {
    function get($key, $attributes) {
        if (!isset($attributes[$key]) || is_null($attributes[$key])) {
        }
    }
};

new class extends DummyProperties {
    function get($key, $attributes) {
        if (!isset($attributes[$key]) || is_null($attributes[$key])) {
        }
    }

    function skip() {
        is_string($this->dummyStringNullable); // Skip: property still could be <null>.
        is_int($this->dummyConstantNullable); // Skip: property still could be <null>.
        is_bool($this->dummyBoolNullable); // Skip: property still could be <null>.
        is_float($this->dummyFloatNull); // Skip: property still could be <null> (initially).
        is_float($this->dummyFloatNullable); // Skip: property still could be <null>.
    }
};

// Skip: unknown result.

(function (SplFileInfo $arg, string $method) {
    is_null($dummy = $arg->$method());
})();
