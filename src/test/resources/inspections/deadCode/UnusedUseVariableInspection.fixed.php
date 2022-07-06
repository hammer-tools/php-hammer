<?php

$dummy = function() use ($b) {
    return $b;
};

$dummy = function() use (&$b) {
    return $b;
};

$dummy = function() {
};

$dummy = dummy(function () use ($a) {
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
