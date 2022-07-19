<?php

$dummy = function ($a, $b) {
    return compact('a', null);
};

$dummy = function ($a, $b) {
    return compact('a', [true]);
};

$dummy = function ($a, $b) {
    return compact(['a', 1 => 123]);
};

$dummy = function ($a, int $b) {
    return compact('a', $b);
};

// Not applicable:

$dummy = function ($a, $b) {
    return compact('a', 'b');
};

$dummy = function ($a, $b) {
    return compact('a', $b, ['b']);
};
