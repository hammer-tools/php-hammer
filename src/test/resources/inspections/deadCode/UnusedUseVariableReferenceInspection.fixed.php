<?php

$dummy = function () use ($a, $b, $c) {
    return $a + $b + $c;
};

$dummy = function () use ($a, &$b, $c) {
    $b = 2;

    return $a + $c;
};
