<?php

$dummy = function ($a, $b) {
    return compact('a', 'a', 'b');
};

$dummy = function ($a, $b) {
    return compact('a', ['a', 'b']);
};

$dummy = function ($a, $b) {
    return compact(['a', 'a'], 'b');
};

$dummy = function ($a, $b) {
    return compact(['a'], 'a', 'b');
};

$dummy = function ($a, $b) {
    return compact('a', ['a'], 'b');
};

$dummy = function ($a, $b) {
    return compact('a', ['b', 'a']);
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
