<?php

$dummy = function () {
    $x = 1;
    $y = 2;
    $z = 3;

    $a = <weak_warning descr="🔨 PHP Hammer: array can be replaced by compact().">['x' => $x]</weak_warning>;
    $b = [];
    $c = ['y'];

    ['x'=>$x] = [];

    $dummy = <weak_warning descr="🔨 PHP Hammer: array can be replaced by compact().">[
        'y' => $y,
        ... <weak_warning descr="🔨 PHP Hammer: array can be replaced by compact().">['z' => $z]</weak_warning>,
        'x' => $x,
        ... compact('a', "b"),
    ]</weak_warning>;

    // Not applicable because of the variable with reference:

    $dummy = ['x' => &$x];
};

// Inspection must not works when inside of a arrow function due to PHP bug #78970.
$dummy = fn() => function () use ($dummy) {
    $dummy = <weak_warning descr="🔨 PHP Hammer: array can be replaced by compact().">[ 'dummy' => $dummy ]</weak_warning>; // Must accept.

    return fn() => [ 'dummy' => $dummy ]; // Must ignore.
};
