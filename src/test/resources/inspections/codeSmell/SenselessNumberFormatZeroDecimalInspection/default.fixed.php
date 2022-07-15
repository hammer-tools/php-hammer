<?php

$dummy = fn($x) => (string)(int)$x;

$dummy = fn($x) => (string)(int)($x + 1);

$dummy = fn($x) => (string)(int)$x;

$dummy = fn($x) => (string)(int)$x;

$dummy = fn() => (string)(int)123.45;

$dummy = fn() => (string)(int)123.45;

$dummy = fn() => (string)(int)123.45;

$dummy = fn() => (string)(int)123;

$dummy = fn() => (string)(int)123;

$dummy = fn() => (string)(int)123;

// Not applicable:

$dummy = fn() => number_format(123, 1);

$dummy = fn($x) => number_format(123, $x);
