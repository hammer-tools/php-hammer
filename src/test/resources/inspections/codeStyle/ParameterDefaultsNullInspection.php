<?php

$dummy = function ($a, <weak_warning descr="[PHP Hammer] Default value of the parameter must be \"null\".">$b = 123</weak_warning>) {
};

$dummy = function (<weak_warning descr="[PHP Hammer] Default value of the parameter must be \"null\".">$a = 123</weak_warning>) {
};

abstract class DummyA
{
    function dummyA($a, <weak_warning descr="[PHP Hammer] Default value of the parameter must be \"null\".">$b = 123</weak_warning>)
    {
    }

    function dummyB($a, <weak_warning descr="[PHP Hammer] Default value of the parameter must be \"null\".">$b = 123</weak_warning>)
    {
        return $b;
    }
}

// Not applicable for quick-fix only:

$dummy = function (<weak_warning descr="[PHP Hammer] Default value of the parameter must be \"null\".">&$a = 123</weak_warning>) {
};

$dummy = function (<weak_warning descr="[PHP Hammer] Default value of the parameter must be \"null\".">int &$a = 123</weak_warning>) {
};

abstract class DummyB
{
    abstract function dummyA($a, <weak_warning descr="[PHP Hammer] Default value of the parameter must be \"null\".">$b = 123</weak_warning>);
}

interface IDummyA
{
    function dummyA($a, <weak_warning descr="[PHP Hammer] Default value of the parameter must be \"null\".">$b = 123</weak_warning>);
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
