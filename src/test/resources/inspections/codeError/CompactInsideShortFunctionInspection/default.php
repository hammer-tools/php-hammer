<?php

$dummy = fn() => <error descr="🔨 PHP Hammer: usage of compact() inside a short function.">compact('dummy')</error>;

$dummy = fn($a) => <error descr="🔨 PHP Hammer: usage of compact() inside a short function.">compact('a', 'dummy')</error>;

$dummy = fn($a) => <error descr="🔨 PHP Hammer: usage of compact() inside a short function.">compact($a)</error>;

// Not applicable:

$dummy = fn($a) => compact('a');

$dummy = function () use ($dummy) {
    return compact('dummy');
};
