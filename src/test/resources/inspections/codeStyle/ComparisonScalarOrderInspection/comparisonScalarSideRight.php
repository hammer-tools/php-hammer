<?php

$dummy = <weak_warning descr="🔨 PHP Hammer: scalar type must be on the right side.">99 !== $x</weak_warning>;
$dummy = <weak_warning descr="🔨 PHP Hammer: scalar type must be on the right side.">false != $y</weak_warning>;
$dummy = <weak_warning descr="🔨 PHP Hammer: scalar type must be on the right side.">\false != $y</weak_warning>;
$dummy = <weak_warning descr="🔨 PHP Hammer: scalar type must be on the right side.">[] != $x</weak_warning>;
$dummy = <weak_warning descr="🔨 PHP Hammer: scalar type must be on the right side.">__FILE__ === $x</weak_warning>;
$dummy = <weak_warning descr="🔨 PHP Hammer: scalar type must be on the right side.">-99 === $x</weak_warning>;
$dummy = <weak_warning descr="🔨 PHP Hammer: scalar type must be on the right side.">-9.9 === $x</weak_warning>;
$dummy = <weak_warning descr="🔨 PHP Hammer: scalar type must be on the right side.">static::class === $x</weak_warning>;

// Not applicable (already on the right side):

$dummy = $x === '';
$dummy = $y == true;
$dummy = $x == 9.9;
$dummy = $x == null;

// Not applicable (both side are scalars):

$dummy = 99 === '99';
$dummy = __FILE__ == __DIR__;
$dummy = static::class == self::class;

// Not applicable:

$dummy = $x instanceof DateTime;
$dummy = $x == $y;
$dummy = trim($x) == $y;
