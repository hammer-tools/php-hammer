<?php

/** @var object $z */

$dummy = (bool)$z;
$dummy = (int)$z;
$dummy = (float)$z;
$dummy = (float)$z;
$dummy = (string)$z;
$dummy = (string)$z;

$z = (bool)$z;
$z = (bool)$z;
$z = (int)$z;
$z = (int)$z;
$z = (float)$z;
$z = (float)$z;
$z = (string)$z;
$z = (string)$z;
$z = (array)$z;
$z = (object)$z;
$z = null;

$dummy = (int)$z;

/** @var object $z */

$dummy = (int)$z::class;
$dummy = (int)(is_object($z) + 1);
$dummy = (int)$z . (int)($z + 1);

// Not applicable:

$dummy = boolval();
$dummy = intval($z, 16);

$dummy = settype($z, 'int');

settype();
settype($z);
settype($z, $x);

// Error:

settype(, "int");
settype(123, "int");
