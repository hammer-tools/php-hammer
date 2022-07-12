<?php

/** @var int $x */

$dummy = "{$x}";
$dummy = "{$$x}";
$dummy = "abc{$x}123";
$dummy = "abc{$$x}123";

// Not applicable:

$dummy = "{$x}";
$dummy = "{$$x}";
$dummy = '${x}';
