<?php

$dummy = <weak_warning descr="[PHP Hammer] String can be simplified.">"$x"</weak_warning>;
$dummy = <weak_warning descr="[PHP Hammer] String can be simplified.">"{$x}"</weak_warning>;

// Not applicable:

$dummy = " $x ";
$dummy = " {$x} ";
