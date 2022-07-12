<?php

$dummy = (string)$x;
$dummy = (string)$x;
$dummy = (string)$x->from;
$dummy = (string)$x->get();
$dummy = (string)$x->from->y();

// Not applicable:

$dummy = " $x ";
$dummy = " {$x} ";
