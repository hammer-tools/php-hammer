<?php

$dummy = '' === $x;
$dummy = true == $y;
$dummy = \true == $y;
$dummy = 9.9 == $x;
$dummy = null == $x;
$dummy = -99 == $x;
$dummy = -9.9 == $x;
$dummy = static::class == $x;

// Not applicable (already on the left side):

$dummy = 99 !== $x;
$dummy = false != $y;
$dummy = [] != $x;
$dummy = __FILE__ === $x;

// Not applicable (both side are scalars):

$dummy = 99 === '99';
$dummy = __FILE__ == __DIR__;
$dummy = static::class == self::class;

// Not applicable:

$dummy = $x instanceof DateTime;
$dummy = $x == $y;
$dummy = trim($x) == $y;

