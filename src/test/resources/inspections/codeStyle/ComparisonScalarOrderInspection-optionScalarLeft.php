<?php

$dummy = <weak_warning descr="[PHP Hammer] Scalar type must be on the left side.">$x === ''</weak_warning>;
$dummy = <weak_warning descr="[PHP Hammer] Scalar type must be on the left side.">$y == true</weak_warning>;
$dummy = <weak_warning descr="[PHP Hammer] Scalar type must be on the left side.">$y == \true</weak_warning>;
$dummy = <weak_warning descr="[PHP Hammer] Scalar type must be on the left side.">$x == 9.9</weak_warning>;
$dummy = <weak_warning descr="[PHP Hammer] Scalar type must be on the left side.">$x == null</weak_warning>;
$dummy = <weak_warning descr="[PHP Hammer] Scalar type must be on the left side.">$x == -9.9</weak_warning>;

// Not applicable:

$dummy = 99 !== $x;
$dummy = false != $y;
$dummy = [] != $x;
$dummy = __FILE__ === $x;

$dummy = 99 === '99';
$dummy = $x instanceof DateTime;
