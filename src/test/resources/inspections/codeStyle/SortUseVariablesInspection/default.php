<?php

$a = 1;
$b = 2;

$dummy = function () use (<weak_warning descr="🔨 PHP Hammer: Unorganized use() variables.">$b, $a</weak_warning>) {
    return $a + $b + 1 + (function () use (<weak_warning descr="🔨 PHP Hammer: Unorganized use() variables.">$b, $a</weak_warning>) {
            return $a + $b + 2;
        })();
};

$dummy = function () use (<weak_warning descr="🔨 PHP Hammer: Unorganized use() variables.">$b, &$a</weak_warning>) {
    $a = $b;
};

$dummy = function () use (<weak_warning descr="🔨 PHP Hammer: Unorganized use() variables.">&$b, $a</weak_warning>) {
    $a = $b;
};

$dummy = function () use (<weak_warning descr="🔨 PHP Hammer: Unorganized use() variables.">$a, $b, $c</weak_warning>) {
    return $b;
};

$dummy = function () use (<weak_warning descr="🔨 PHP Hammer: Unorganized use() variables.">$a, $b, $c, $d, $e, $f, $g, $h, $i</weak_warning>) {
    return $a + $c + $e + $f + $i;
};

// Not applicable:

$dummy = function () use ($a) {
};
