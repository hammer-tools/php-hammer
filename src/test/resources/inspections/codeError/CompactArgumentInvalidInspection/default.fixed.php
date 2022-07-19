<?php

$dummy = function ($a, $b) {
    return compact('a');
};

$dummy = function ($a, $b) {
    return compact('a');
};

$dummy = function ($a, $b) {
    return compact(['a']);
};

$dummy = function ($a, int $b) {
    return compact('a');
};

// Not applicable:

$dummy = function ($a, $b) {
    return compact('a', 'b');
};

$dummy = function ($a, $b) {
    return compact('a', $b, ['b']);
};

$dummy = function ($a, string $b) {
    return compact('a', $b);
};

$dummy = function ($a) {
    return compact(... $a);
};

$dummy = function ($a) {
    return compact(... [$a]);
};
