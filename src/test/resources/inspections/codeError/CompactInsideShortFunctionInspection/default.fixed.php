<?php

$dummy = fn() => ['dummy' => $dummy];

$dummy = fn($a) => ['a' => $a, 'dummy' => $dummy, 'dummy2' => $dummy2];

$dummy = fn($a) => ['a' => $a];

// No quick-fix:

$dummy = fn() => compact(123);

// Not applicable:

$dummy = fn($a) => compact('a');

$dummy = function () use ($dummy) {
    return compact('dummy');
};
