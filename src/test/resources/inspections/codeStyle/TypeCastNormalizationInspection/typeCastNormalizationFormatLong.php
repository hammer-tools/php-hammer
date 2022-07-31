<?php

$dummy = <weak_warning descr="🔨 PHP Hammer: type cast must be written as (integer).">(int)</weak_warning> $x;
$dummy = <weak_warning descr="🔨 PHP Hammer: type cast must be written as (float).">(double)</weak_warning> $x;
$dummy = <weak_warning descr="🔨 PHP Hammer: type cast must be written as (float).">(real)</weak_warning> $x;
$dummy = <weak_warning descr="🔨 PHP Hammer: type cast must be written as (boolean).">(bool)</weak_warning> $x;
$dummy = <weak_warning descr="🔨 PHP Hammer: type cast must be written as (boolean).">(BOOL)</weak_warning> $x;
$dummy = <weak_warning descr="🔨 PHP Hammer: type cast must be written as (boolean).">( boolean )</weak_warning> $x;

// Not applicable:

$dummy = (integer) $x;
$dummy = (float) $x;
$dummy = (boolean) $x;
