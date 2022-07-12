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
    abstract function dummyA($a, <weak_warning descr="[PHP Hammer] Default value of the parameter must be \"null\".">$b = 123</weak_warning>);
}

interface IDummyA
{
    function dummyA($a, <weak_warning descr="[PHP Hammer] Default value of the parameter must be \"null\".">$b = 123</weak_warning>);
}

interface IDummyC
{
    function dummyA(<weak_warning descr="[PHP Hammer] Default value of the parameter must be \"null\".">int $b = 123</weak_warning>);
}

// Keep inspecting another cases:

$dummy = function ($a, <weak_warning descr="[PHP Hammer] Default value of the parameter must be \"null\".">$b = 123</weak_warning>) {
};
