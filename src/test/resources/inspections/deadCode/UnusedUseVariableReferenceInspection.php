<?php

$dummy = function () use ($a, <warning descr="[PHP Hammer] Unused reference for variable declared in use().">&</warning>$b, $c) {
    return $a + $b + $c;
};

$dummy = function () use (<warning descr="[PHP Hammer] Unused reference for variable declared in use().">&</warning>$a, &$b, <warning descr="[PHP Hammer] Unused reference for variable declared in use().">&</warning>$c) {
    $b = 2;

    return $a + $c;
};
