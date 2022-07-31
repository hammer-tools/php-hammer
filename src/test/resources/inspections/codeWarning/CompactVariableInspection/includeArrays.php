<?php

$dummy = function ($a, $b, $c) {
    return compact('a', 'b', <warning descr="🔨 PHP Hammer: variables should be avoided in compact().">$c</warning>);
};

// Not applicable:

$dummy = function ($a, $b, array $c) {
    return compact('a', 'b', $c);
};

$dummy = function ($a, $b, $c) {
    return compact('a', 'b', 'c');
};

$dummy = function ($a, $b, $c) {
    return compact(['a'], ['b', 'c']);
};
