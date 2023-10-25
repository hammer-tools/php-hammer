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
    <weak_warning descr="🔨 PHP Hammer: useless is_scalar(): $dummyInt is always scalar.">is_scalar($dummyInt)</weak_warning>;

    // Always integer:
    <weak_warning descr="🔨 PHP Hammer: useless is_int(): argument is always integer.">is_int(123)</weak_warning>;
    <weak_warning descr="🔨 PHP Hammer: useless is_int(): $dummyInt is always integer.">is_int($dummyInt)</weak_warning>;
    <weak_warning descr="🔨 PHP Hammer: useless is_int(): $dummyInt is always integer.">is_int($dummyInt)</weak_warning> === true;
    <weak_warning descr="🔨 PHP Hammer: useless is_int(): $dummyInt is always integer.">is_int($dummyInt)</weak_warning> !== true;
    <weak_warning descr="🔨 PHP Hammer: useless is_integer(): $dummyInt is always integer.">is_integer($dummyInt)</weak_warning>;
    <weak_warning descr="🔨 PHP Hammer: useless is_long(): $dummyInt is always integer.">is_long($dummyInt)</weak_warning>;

    // Never float:
    <weak_warning descr="🔨 PHP Hammer: useless is_float(): $dummyInt is never float.">is_float($dummyInt)</weak_warning>;
    <weak_warning descr="🔨 PHP Hammer: useless is_float(): argument is never float.">is_float(123)</weak_warning>;

    // Complex quick-fixes:
    if (<weak_warning descr="🔨 PHP Hammer: useless is_int(): $dummyInt is always integer.">is_int($dummyInt)</weak_warning>) {
        assert(<weak_warning descr="🔨 PHP Hammer: useless is_int(): $dummyInt is always integer.">is_int($dummyInt)</weak_warning>);
        assert(<weak_warning descr="🔨 PHP Hammer: useless is_float(): $dummyInt is never float.">is_float($dummyInt)</weak_warning>);

        return <weak_warning descr="🔨 PHP Hammer: useless is_int(): $dummyInt is never integer.">is_int($dummyInt)</weak_warning>;
    }
})();

(function () {
    $dummyFloat = 123.45;

    // Always scalar:
    <weak_warning descr="🔨 PHP Hammer: useless is_scalar(): argument is always scalar.">is_scalar(123.45)</weak_warning>;
    <weak_warning descr="🔨 PHP Hammer: useless is_scalar(): $dummyFloat is always scalar.">is_scalar($dummyFloat)</weak_warning>;

    // Always float:
    <weak_warning descr="🔨 PHP Hammer: useless is_float(): $dummyFloat is always float.">is_float($dummyFloat)</weak_warning>;
    <weak_warning descr="🔨 PHP Hammer: useless is_double(): $dummyFloat is always float.">is_double($dummyFloat)</weak_warning>;

    // Never integer:
    <weak_warning descr="🔨 PHP Hammer: useless is_int(): $dummyFloat is never integer.">is_int($dummyFloat)</weak_warning>;
})();

(function () {
    $dummyString = 'dummy';

    // Always scalar:
    <weak_warning descr="🔨 PHP Hammer: useless is_scalar(): argument is always scalar.">is_scalar('dummy')</weak_warning>;
    <weak_warning descr="🔨 PHP Hammer: useless is_scalar(): $dummyString is always scalar.">is_scalar($dummyString)</weak_warning>;

    // Always string:
    <weak_warning descr="🔨 PHP Hammer: useless is_string(): $dummyString is always string.">is_string($dummyString)</weak_warning>;

    // Never integer:
    <weak_warning descr="🔨 PHP Hammer: useless is_int(): $dummyString is never integer.">is_int($dummyString)</weak_warning>;
})();

(function () {
    $dummyBooleanTrue = true;

    // Always scalar:
    <weak_warning descr="🔨 PHP Hammer: useless is_scalar(): argument is always scalar.">is_scalar(true)</weak_warning>;
    <weak_warning descr="🔨 PHP Hammer: useless is_scalar(): $dummyBooleanTrue is always scalar.">is_scalar($dummyBooleanTrue)</weak_warning>;

    // Always boolean:
    <weak_warning descr="🔨 PHP Hammer: useless is_bool(): $dummyBooleanTrue is always boolean.">is_bool($dummyBooleanTrue)</weak_warning>;

    // Never integer:
    <weak_warning descr="🔨 PHP Hammer: useless is_int(): $dummyBooleanTrue is never integer.">is_int($dummyBooleanTrue)</weak_warning>;
})();

(function () {
    $dummyBooleanFalse = false;

    // Always scalar:
    <weak_warning descr="🔨 PHP Hammer: useless is_scalar(): argument is always scalar.">is_scalar(false)</weak_warning>;
    <weak_warning descr="🔨 PHP Hammer: useless is_scalar(): $dummyBooleanFalse is always scalar.">is_scalar($dummyBooleanFalse)</weak_warning>;

    // Always boolean:
    <weak_warning descr="🔨 PHP Hammer: useless is_bool(): $dummyBooleanFalse is always boolean.">is_bool($dummyBooleanFalse)</weak_warning>;

    // Never integer:
    <weak_warning descr="🔨 PHP Hammer: useless is_int(): $dummyBooleanFalse is never integer.">is_int($dummyBooleanFalse)</weak_warning>;
})();

(function () {
    $dummyNull = null;

    // Always null:
    <weak_warning descr="🔨 PHP Hammer: useless is_null(): argument is always null.">is_null(null)</weak_warning>;
    <weak_warning descr="🔨 PHP Hammer: useless is_null(): $dummyNull is always null.">is_null($dummyNull)</weak_warning>;

    // Never integer:
    <weak_warning descr="🔨 PHP Hammer: useless is_int(): $dummyNull is never integer.">is_int($dummyNull)</weak_warning>;

    // Never scalar:
    <weak_warning descr="🔨 PHP Hammer: useless is_scalar(): $dummyNull is never scalar.">is_scalar($dummyNull)</weak_warning>;
})();

(function () {
    $dummyObject = new SplFileInfo();

    // Always object:
    <weak_warning descr="🔨 PHP Hammer: useless is_object(): argument is always object.">is_object(new SplFileInfo())</weak_warning>;
    <weak_warning descr="🔨 PHP Hammer: useless is_object(): $dummyObject is always object.">is_object($dummyObject)</weak_warning>;

    // Never integer:
    <weak_warning descr="🔨 PHP Hammer: useless is_int(): $dummyObject is never integer.">is_int($dummyObject)</weak_warning>;

    // Never scalar:
    <weak_warning descr="🔨 PHP Hammer: useless is_scalar(): $dummyObject is never scalar.">is_scalar($dummyObject)</weak_warning>;
})();

// Report: return used as argument.

(function () {
    // Always integer:
    <weak_warning descr="🔨 PHP Hammer: useless is_int(): getInteger() is always integer.">is_int(getInteger())</weak_warning>;

    // Never integer:
    <weak_warning descr="🔨 PHP Hammer: useless is_int(): getResource() is never integer.">is_int(getResource())</weak_warning>;
    <weak_warning descr="🔨 PHP Hammer: useless is_int(): getVoid() is never integer.">is_int(getVoid())</weak_warning>;
    <weak_warning descr="🔨 PHP Hammer: useless is_int(): getNever() is never integer.">is_int(getNever())</weak_warning>;
})();

(function () {
    $instance = new DummyProperties;

    // Always integer:
    <weak_warning descr="🔨 PHP Hammer: useless is_int(): argument is always integer.">is_int((new DummyProperties)->dummyInt)</weak_warning>;
    <weak_warning descr="🔨 PHP Hammer: useless is_int(): argument is always integer.">is_int($instance->dummyInt)</weak_warning>;

    // Never float:
    <weak_warning descr="🔨 PHP Hammer: useless is_float(): argument is never float.">is_float((new DummyProperties)->dummyInt)</weak_warning>;
    <weak_warning descr="🔨 PHP Hammer: useless is_float(): argument is never float.">is_float($instance->dummyInt)</weak_warning>;

    // Never string:
    <weak_warning descr="🔨 PHP Hammer: useless is_string(): argument is never string.">is_string((new DummyProperties)->dummyFloatNullable)</weak_warning>;
    <weak_warning descr="🔨 PHP Hammer: useless is_string(): argument is never string.">is_string($instance->dummyFloatNullable)</weak_warning>;
})();

(function () {
    /** @var int|null $dummyIntNullable */
    $dummyIntNullable = 123;

    // Never float:
    <weak_warning descr="🔨 PHP Hammer: useless is_float(): $dummyIntNullable is never float.">is_float($dummyIntNullable)</weak_warning>;
})();

(function () {
    $dummyResourceOrFalse = getResourceOrFalse();

    // Never integer:
    <weak_warning descr="🔨 PHP Hammer: useless is_int(): $dummyResourceOrFalse is never integer.">is_int($dummyResourceOrFalse)</weak_warning>;
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
