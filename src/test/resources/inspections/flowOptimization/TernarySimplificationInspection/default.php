<?php

$dummy = <weak_warning descr="🔨 PHP Hammer: ternary can be simplified.">$y === $x ? $x : $y</weak_warning>;

$dummy = <weak_warning descr="🔨 PHP Hammer: ternary can be simplified.">$y !== $x ? $y : $x</weak_warning>;

// Not applicable;

$dummy = $y === $x ? $y : $x;

$dummy = $y !== $x ? $x : $y;

$dummy = $x ?: $y;
