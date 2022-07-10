<?php

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

// Keep inspecting another cases:

$dummy = function ($a, <weak_warning descr="[PHP Hammer] Default value of the parameter must be \"null\".">$b = 123</weak_warning>) {
};
