<?php

$dummy = function ($a, $b = null) {
    $b = $b === null ? 123 : $b;
};

$dummy = function ($a = null) {
    $a = $a === null ? 123 : $a;
};

$dummy = function (bool|null $a = null) {
    $a = $a === null ? false : $a;
};

$dummy = function (bool|null $a = null) {
    $a = $a === null ? false : $a;
};

$dummy = function(int|null $a = null) {
    $a = $a === null ? 0 : $a;
};

abstract class DummyA
{
    function dummyA($a, $b = null)
    {
        $b = $b === null ? 123 : $b;
    }

    function dummyB($a, $b = null)
    {
        $b = $b === null ? 123 : $b;
        return $b;
    }
}

abstract class DummyB
{
    abstract function dummyA($a, $b = 123);
}

interface IDummyA
{
    function dummyA($a, $b = 123);
}

interface IDummyC
{
    function dummyA(int $b = 123);
}

// Not applicable for quick-fix only:

$dummy = function (&$a = 123) {
};

$dummy = function (int &$a = 123) {
};

// Dummy:

interface IDummyB
    extends IDummyA
{
}

abstract class DummyC
    extends DummyB
{
}

// Not applicable:

$dummy = function ($a) {
};

$dummy = function ($a = null) {
};

class DummyD
    extends DummyB
{
    function dummyA($a, $b = 123)
    {
    }
}

$dummy = new class
    extends DummyB {
    function dummyA($a, $b = 123)
    {
    }
};

$dummy = new class
    extends DummyC {
    function dummyA($a, $b = 123)
    {
    }
};

$dummy = new class
    implements IDummyB {
    function dummyA($a, $b = 123)
    {
    }
};
