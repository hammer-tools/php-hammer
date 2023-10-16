<?php

$dummy = function(<weak_warning descr="🔨 PHP Hammer: default value of the parameter must be \"null\".">?int $a = 0</weak_warning>, int $last = 0) {
};

$dummy = function (<weak_warning descr="🔨 PHP Hammer: default value of the parameter must be \"null\".">bool $a = false</weak_warning>, int $last = 0) {
};

$dummy = function (<weak_warning descr="🔨 PHP Hammer: default value of the parameter must be \"null\".">boolean $a = false</weak_warning>, int $last = 0) {
};

// Not applicable because parameter is nullable/untyped:

$dummy = function ($a, $b = 123) {
};

$dummy = function ($a = 123) {
};

$dummy = function (string|null $a = '') {
};

abstract class DummyA
{
    function dummyA($a, $b = 123)
    {
    }

    function dummyB($a, $b = 123)
    {
        return $b;
    }
}

$dummy = function (&$a = 123) {
};

class DummyF
{
    function __construct(private readonly int $a = 123, <weak_warning descr="🔨 PHP Hammer: default value of the parameter must be \"null\".">int $b = 123</weak_warning>, $c = null)
    {
    }
}

class DummyG
{
    function __construct(<weak_warning descr="🔨 PHP Hammer: default value of the parameter must be \"null\".">private int $a = 123</weak_warning>, $b = null)
    {
    }
}

// Not applicable, abstract methods:

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

$dummy = function (int &$a = 123) {
};

$dummy = function ($a) {
};

$dummy = function ($a = null) {
};

$dummy = function () {
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

class DummyE
{
    function dummy(int $a = 123)
    {
    }
}

$dummy = new class
    extends DummyE {
    function dummy(int $a = 123)
    {
    }
};

class DummyF2
{
    function __construct(private readonly int $a = 123, int|null $b = null, $c = null)
    {
    }
}
