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
