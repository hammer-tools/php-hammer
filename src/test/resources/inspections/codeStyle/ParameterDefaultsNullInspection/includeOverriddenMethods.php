<?php

class DummyE
{
    function dummy(<weak_warning descr="🔨 PHP Hammer: Default value of the parameter must be \"null\".">int $a = 123</weak_warning>)
    {
    }
}

$dummy = new class
    extends DummyE {
    function dummy(int $a = 123)
    {
    }
};
