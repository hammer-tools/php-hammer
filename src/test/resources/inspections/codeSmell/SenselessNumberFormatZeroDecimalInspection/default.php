<?php

$dummy = fn($x) => <weak_warning descr="🔨 PHP Hammer: Senseless number_format() using zero decimal point.">number_format($x)</weak_warning>;

$dummy = fn($x) => <weak_warning descr="🔨 PHP Hammer: Senseless number_format() using zero decimal point.">number_format($x + 1)</weak_warning>;

$dummy = fn($x) => <weak_warning descr="🔨 PHP Hammer: Senseless number_format() using zero decimal point.">number_format($x, 0)</weak_warning>;

$dummy = fn($x) => <weak_warning descr="🔨 PHP Hammer: Senseless number_format() using zero decimal point.">number_format($x, 0, '.', '')</weak_warning>;

$dummy = fn() => <weak_warning descr="🔨 PHP Hammer: Senseless number_format() using zero decimal point.">number_format(123.45)</weak_warning>;

$dummy = fn() => <weak_warning descr="🔨 PHP Hammer: Senseless number_format() using zero decimal point.">number_format(123.45, 0)</weak_warning>;

$dummy = fn() => <weak_warning descr="🔨 PHP Hammer: Senseless number_format() using zero decimal point.">number_format(123.45, 0, '.', '')</weak_warning>;

$dummy = fn() => <weak_warning descr="🔨 PHP Hammer: Senseless number_format() using zero decimal point.">number_format(123)</weak_warning>;

$dummy = fn() => <weak_warning descr="🔨 PHP Hammer: Senseless number_format() using zero decimal point.">number_format(123, 0)</weak_warning>;

$dummy = fn() => <weak_warning descr="🔨 PHP Hammer: Senseless number_format() using zero decimal point.">number_format(123, 0, '.', '')</weak_warning>;

// Not applicable:

$dummy = fn() => number_format(123, 1);

$dummy = fn($x) => number_format(123, $x);
