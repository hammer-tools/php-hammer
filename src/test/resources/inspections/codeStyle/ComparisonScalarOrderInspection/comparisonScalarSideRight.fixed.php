<?php

$dummy = $x !== 99;
$dummy = $y != false;
$dummy = $y != \false;
$dummy = $x != [];
$dummy = $x === __FILE__;
$dummy = $x === -99;
$dummy = $x === -9.9;
$dummy = $x === static::class;
$dummy = ($dummy = 123) === 123;

// Not applicable (already on the right side):

$dummy = $x === '';
$dummy = $y == true;
$dummy = $x == 9.9;
$dummy = $x == null;

// Not applicable (both side are scalars):

$dummy = 99 === '99';
$dummy = __FILE__ == __DIR__;
$dummy = static::class == self::class;

// Not applicable (contains assignment on right side):

$dummy = 123 === $dummy = 123;
$dummy = 123 === $dummy += 123;
$dummy = 123 === $dummy ??= 123;

// Not applicable:

$dummy = $x instanceof DateTime;
$dummy = $x == $y;
$dummy = trim($x) == $y;
