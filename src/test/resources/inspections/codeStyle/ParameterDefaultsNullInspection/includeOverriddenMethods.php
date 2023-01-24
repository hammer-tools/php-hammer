<?php

class DummyE
{
    function dummy(<weak_warning descr="🔨 PHP Hammer: default value of the parameter must be \"null\".">int $a = 123</weak_warning>, int $last = 123)
    {
    }
}

$dummy = new class
    extends DummyE {
    function dummy(int $a = 123)
    {
    }
};
