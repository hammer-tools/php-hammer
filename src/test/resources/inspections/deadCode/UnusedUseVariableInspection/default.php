<?php

$dummy = function() use (<weak_warning descr="[PHP Hammer] Unused variable declared in use().">$a</weak_warning>, $b, <weak_warning descr="[PHP Hammer] Unused variable declared in use().">$c</weak_warning>) {
    return $b;
};

$dummy = function() use (<weak_warning descr="[PHP Hammer] Unused variable declared in use().">&$a</weak_warning>, &$b, <weak_warning descr="[PHP Hammer] Unused variable declared in use().">&$c</weak_warning>) {
    return $b;
};

$dummy = function() use (<weak_warning descr="[PHP Hammer] Unused variable declared in use().">$any</weak_warning>) {
};

$dummy = dummy(function () use ($a, <weak_warning descr="[PHP Hammer] Unused variable declared in use().">$b</weak_warning>) {
    return $a;
});

// Not applicable:

$dummy = function () {
    $a = 0;

    $innerDummy = function () use (&$a) {
        return $a;
    };

    $a = 1;
};

$dummy = function () {
    $a = [];

    $innerDummy = function () use (&$a) {
        return $a;
    };

    array_push($a, 1);
};
