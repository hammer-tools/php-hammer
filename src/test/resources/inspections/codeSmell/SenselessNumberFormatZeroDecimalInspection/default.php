<?php

$dummy = fn($x) => <weak_warning descr="🔨 PHP Hammer: senseless number_format() using zero decimal point.">number_format($x, 0, '.', '')</weak_warning>;

$dummy = fn() => <weak_warning descr="🔨 PHP Hammer: senseless number_format() using zero decimal point.">number_format(123.45, 0, '.', '')</weak_warning>;

$dummy = fn() => <weak_warning descr="🔨 PHP Hammer: senseless number_format() using zero decimal point.">number_format(123, 0, '.', '')</weak_warning>;

// Not applicable:

$dummy = fn() => number_format(123, 1);

$dummy = fn($x) => number_format(123, $x);

$dummy = fn($x) => number_format($x);

$dummy = fn($x) => number_format($x + 1);

$dummy = fn($x) => number_format($x, 0);

$dummy = fn() => number_format(123.45);

$dummy = fn() => number_format(123.45, 0);

$dummy = fn() => number_format(123);

$dummy = fn() => number_format(123, 0);

$dummy = fn($x) => number_format($x, 0, '.', $x);

$dummy = fn($x) => number_format($x, 0, '', '.');
