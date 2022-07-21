<?php

$dummy = function (string $x) {
    $x->dummy();
};

$dummy = function (string|null $x) {
    $x->dummy();
};

// Not applicable:

$dummy = function ($x) {
    $x->dummy();
};

$dummy = function ($x = null) {
    $x->dummy();
};

$dummy = function (DateTime $x) {
    $x->dummy();
};

$dummy = function (DateTime $x) {
    $x->dummy();
};

$dummy = function (DateTime|string $x) {
    $x->dummy();
};
