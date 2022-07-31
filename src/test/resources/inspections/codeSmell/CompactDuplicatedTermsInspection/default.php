<?php

$dummy = function ($a, $b) {
    return compact('a', <weak_warning descr="🔨 PHP Hammer: duplicated term in compact().">'a'</weak_warning>, 'b');
};

$dummy = function ($a, $b) {
    return compact('a', [<weak_warning descr="🔨 PHP Hammer: duplicated term in compact().">'a'</weak_warning>, 'b']);
};

$dummy = function ($a, $b) {
    return compact(['a', <weak_warning descr="🔨 PHP Hammer: duplicated term in compact().">'a'</weak_warning>], 'b');
};

$dummy = function ($a, $b) {
    return compact(['a'], <weak_warning descr="🔨 PHP Hammer: duplicated term in compact().">'a'</weak_warning>, 'b');
};

$dummy = function ($a, $b) {
    return compact('a', [<weak_warning descr="🔨 PHP Hammer: duplicated term in compact().">'a'</weak_warning>], 'b');
};

$dummy = function ($a, $b) {
    return compact('a', ['b', <weak_warning descr="🔨 PHP Hammer: duplicated term in compact().">'a'</weak_warning>]);
};

// Not applicable:

$dummy = function ($a, $b) {
    return compact('a', 'b');
};

$dummy = function ($a, $b) {
    return compact(['a'], ['b']);
};

$dummy = function ($a, $b) {
    return compact(['a', 'b']);
};

$dummy = function ($a) {
    return compact(... $a);
};

$dummy = function ($a) {
    return compact(... [$a]);
};

$dummy = function ($a) {
    return compact(... ['a']);
};
