<?php

$dummy = function ($a, $b) {
    return compact('a', <error descr="🔨 PHP Hammer: Invalid argument for compact() function.">null</error>);
};

$dummy = function ($a, $b) {
    return compact('a', [<error descr="🔨 PHP Hammer: Invalid argument for compact() function.">true</error>]);
};

$dummy = function ($a, $b) {
    return compact(['a', 1 => <error descr="🔨 PHP Hammer: Invalid argument for compact() function.">123</error>]);
};

$dummy = function ($a, int $b) {
    return compact('a', <error descr="🔨 PHP Hammer: Invalid argument for compact() function.">$b</error>);
};

// Not applicable:

$dummy = function ($a, $b) {
    return compact('a', 'b');
};

$dummy = function ($a, $b) {
    return compact('a', $b, ['b']);
};

$dummy = function ($a, string $b) {
    return compact('a', $b);
};
