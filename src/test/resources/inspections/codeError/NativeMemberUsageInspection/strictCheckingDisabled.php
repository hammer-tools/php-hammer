<?php

$dummy = function (string $x) {
    <error descr="🔨 PHP Hammer: native type must not be used as object.">$x</error>->dummy();
};

// Not applicable:

$dummy = function (DateTime|string $x) {
    $x->dummy();
};
