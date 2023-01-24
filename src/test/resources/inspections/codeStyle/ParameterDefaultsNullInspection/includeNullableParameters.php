<?php

$dummy = function ($a, <weak_warning descr="🔨 PHP Hammer: default value of the parameter must be \"null\".">$b = 123</weak_warning>, $last = 123) {
};

$dummy = function (<weak_warning descr="🔨 PHP Hammer: default value of the parameter must be \"null\".">$a = 123</weak_warning>, $last = 123) {
};

abstract class DummyA
{
    function dummyA($a, <weak_warning descr="🔨 PHP Hammer: default value of the parameter must be \"null\".">$b = 123</weak_warning>, $last = 123)
    {
    }

    function dummyB($a, <weak_warning descr="🔨 PHP Hammer: default value of the parameter must be \"null\".">$b = 123</weak_warning>, $last = 123)
    {
        return $b;
    }
}

$dummy = function (<weak_warning descr="🔨 PHP Hammer: default value of the parameter must be \"null\".">string|null $a = ''</weak_warning>, int|null $last = 123) {
};
