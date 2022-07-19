<?php

$dummy = function ($a, $b, $c) {
    return compact('a', 'b', 'c');
};

// Not applicable:

$dummy = function ($a, $b, string $c) {
    return compact('a', 'b', $c);
};

$dummy = function ($a, $b, string $c) {
    return compact('a', ['b', $c]);
};

$dummy = function ($a, $b, $c) {
    return compact('a', 'b', 'c');
};

$dummy = function ($a, $b, $c) {
    return compact(['a'], ['b', 'c']);
};
