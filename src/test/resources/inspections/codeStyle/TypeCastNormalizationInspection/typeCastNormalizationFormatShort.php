<?php

$dummy = <weak_warning descr="🔨 PHP Hammer: Type cast must be written as (int)">(integer)</weak_warning> $x;
$dummy = <weak_warning descr="🔨 PHP Hammer: Type cast must be written as (float)">(double)</weak_warning> $x;
$dummy = <weak_warning descr="🔨 PHP Hammer: Type cast must be written as (float)">(real)</weak_warning> $x;
$dummy = <weak_warning descr="🔨 PHP Hammer: Type cast must be written as (bool)">(BOOL)</weak_warning> $x;
$dummy = <weak_warning descr="🔨 PHP Hammer: Type cast must be written as (bool)">(boolean)</weak_warning> $x;
$dummy = <weak_warning descr="🔨 PHP Hammer: Type cast must be written as (bool)">( bool )</weak_warning> $x;

// Not applicable:

$dummy = (int) $x;
$dummy = (float) $x;
$dummy = (bool) $x;
