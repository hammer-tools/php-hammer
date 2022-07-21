<?php

$dummy = function (string $x) {
    <error descr="🔨 PHP Hammer: Native type must not be used as object.">$x</error>->dummy();
};

// Not applicable:

$dummy = function (DateTime|string $x) {
    $x->dummy();
};
