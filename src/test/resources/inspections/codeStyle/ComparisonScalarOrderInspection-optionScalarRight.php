<?php

$dummy = <weak_warning descr="[PHP Hammer] Scalar type must be on the right side.">99 !== $x</weak_warning>;
$dummy = <weak_warning descr="[PHP Hammer] Scalar type must be on the right side.">false != $y</weak_warning>;
$dummy = <weak_warning descr="[PHP Hammer] Scalar type must be on the right side.">\false != $y</weak_warning>;
$dummy = <weak_warning descr="[PHP Hammer] Scalar type must be on the right side.">[] != $x</weak_warning>;
$dummy = <weak_warning descr="[PHP Hammer] Scalar type must be on the right side.">__FILE__ === $x</weak_warning>;
$dummy = <weak_warning descr="[PHP Hammer] Scalar type must be on the right side.">-9.9 === $x</weak_warning>;

// Not applicable:

$dummy = $x === '';
$dummy = $y == true;
$dummy = $x == 9.9;
$dummy = $x == null;

$dummy = 99 === '99';
$dummy = $x instanceof DateTime;
