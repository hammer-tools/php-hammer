<?php

$dummy = fn() => <error descr="🔨 PHP Hammer: usage of compact() inside a short function.">compact('dummy')</error>;

$dummy = fn($a) => <error descr="🔨 PHP Hammer: usage of compact() inside a short function.">compact('a', 'dummy', 'dummy2')</error>;

$dummy = fn($a) => <error descr="🔨 PHP Hammer: usage of compact() inside a short function.">compact($a)</error>;

// No quick-fix:

$dummy = fn() => <error descr="🔨 PHP Hammer: usage of compact() inside a short function.">compact(123)</error>;

// Not applicable:

$dummy = fn($a) => compact('a');

$dummy = function () use ($dummy) {
    return compact('dummy');
};
