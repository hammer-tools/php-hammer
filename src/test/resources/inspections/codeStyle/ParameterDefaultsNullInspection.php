<?php

$dummy = function ($a, <weak_warning descr="[PHP Hammer] Default value of the parameter must be \"null\".">$b = 123</weak_warning>) {
};

$dummy = function (<weak_warning descr="[PHP Hammer] Default value of the parameter must be \"null\".">$a = 123</weak_warning>) {
};

abstract class Dummy1
{
    function dummy2($a, <weak_warning descr="[PHP Hammer] Default value of the parameter must be \"null\".">$b = 123</weak_warning>)
    {
    }

    function dummy3($a, <weak_warning descr="[PHP Hammer] Default value of the parameter must be \"null\".">$b = 123</weak_warning>)
    {
        return $b;
    }
}

// Not applicable for quick-fix only:

$dummy = function (<weak_warning descr="[PHP Hammer] Default value of the parameter must be \"null\".">&$a = 123</weak_warning>) {
};

$dummy = function (<weak_warning descr="[PHP Hammer] Default value of the parameter must be \"null\".">int &$a = 123</weak_warning>) {
};

abstract class Dummy2
{
    abstract function dummy1($a, <weak_warning descr="[PHP Hammer] Default value of the parameter must be \"null\".">$b = 123</weak_warning>);
}

// Not applicable:

$dummy = function ($a) {
};

$dummy = function ($a = null) {
};
