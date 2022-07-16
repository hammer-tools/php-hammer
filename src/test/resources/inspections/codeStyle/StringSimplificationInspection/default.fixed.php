<?php

$dummy = (string)$x;
$dummy = (string)$x;
$dummy = (string)$x;
$dummy = (string)$x->from;
$dummy = (string)$x->get();
$dummy = (string)$x->from->y();

$dummy = [$x => $x];
$dummy = [$x => $x];
$dummy = [$x => $x];
$dummy = [$x->from => $x];
$dummy = [$x->get() => $x];
$dummy = [$x->from->get() => $x];

// Not applicable:

$dummy = " $x ";
$dummy = " {$x} ";
