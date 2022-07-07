<?php

$a = 1;
$b = 2;

$dummy = function () use ($a, $b) {
    return $a + $b + 1 + (function () use ($a, $b) {
            return $a + $b + 2;
        })();
};

$dummy = function () use (&$a, $b) {
    $a = $b;
};

$dummy = function () use ($a, &$b) {
    $a = $b;
};

$dummy = function () use ($b, $a, $c) {
    return $b;
};

$dummy = function () use ($a, $c, $e, $f, $i, $b, $d, $g, $h) {
    return $a + $c + $e + $f + $i;
};

// Not applicable:

$dummy = function () use ($a) {
};
