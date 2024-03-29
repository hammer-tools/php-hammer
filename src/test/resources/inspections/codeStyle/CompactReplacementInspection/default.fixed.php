<?php

$dummy = function () {
    $x = 1;
    $y = 2;
    $z = 3;

    $a = compact('x');
    $b = [];
    $c = ['y'];

    ['x'=>$x] = [];

    $dummy = compact('y', 'z', 'x', 'a', 'b');

    // Not applicable because of the variable with reference:

    $dummy = ['x' => &$x];
};

// Inspection must not works when inside of a arrow function due to PHP bug #78970.
$dummy = fn() => function () use ($dummy) {
    $dummy = compact('dummy'); // Must accept.

    return fn() => [ 'dummy' => $dummy ]; // Must ignore.
};
