<?php

$dummy = function ($a, $b) {
    return compact('a', 'b');
};

$dummy = function ($a, $b) {
    return compact('a', ['b']);
};

$dummy = function ($a, $b) {
    return compact(['a'], 'b');
};

$dummy = function ($a, $b) {
    return compact(['a'], 'b');
};

$dummy = function ($a, $b) {
    return compact('a', 'b');
};

$dummy = function ($a, $b) {
    return compact('a', ['b']);
};

// Not applicable:

$dummy = function ($a, $b) {
    return compact('a', 'b');
};

$dummy = function ($a, $b) {
    return compact(['a'], ['b']);
};

$dummy = function ($a, $b) {
    return compact(['a', 'b']);
};

$dummy = function ($a) {
    return compact(... $a);
};

$dummy = function ($a) {
    return compact(... [$a]);
};

$dummy = function ($a) {
    return compact(... ['a']);
};
