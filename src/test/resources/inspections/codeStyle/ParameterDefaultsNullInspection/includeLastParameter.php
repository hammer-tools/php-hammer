<?php

$dummy = function (<weak_warning descr="🔨 PHP Hammer: default value of the parameter must be \"null\".">int $a = 123</weak_warning>) {
};

// Not applicable:

$dummy = new class {
    function __construct(private readonly string $a = 'default')
    {
    }
};
