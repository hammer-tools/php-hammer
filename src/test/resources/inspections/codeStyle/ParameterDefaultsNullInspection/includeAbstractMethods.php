<?php

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
    function dummyA(<weak_warning descr="🔨 PHP Hammer: default value of the parameter must be \"null\".">int $b = 123</weak_warning>, int $last = 123);
}
