<?php

$dummy = function (string $x) {
    <error descr="🔨 PHP Hammer: Native type must not be used as object.">$x</error>->dummy();
};

$dummy = function (string|null $x) {
    <error descr="🔨 PHP Hammer: Native type must not be used as object.">$x</error>->dummy();
};

// Not applicable:

$dummy = function ($x) {
    $x->dummy();
};

$dummy = function ($x = null) {
    $x->dummy();
};

$dummy = function (mixed $x) {
    $x->dummy();
};

$dummy = function (DateTime $x) {
    $x->dummy();
};

$dummy = function (DateTime|string $x) {
    $x->dummy();
};
