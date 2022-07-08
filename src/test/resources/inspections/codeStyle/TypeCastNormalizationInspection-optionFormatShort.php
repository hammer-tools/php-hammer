<?php

$dummy = (int) $x;
$dummy = <weak_warning descr="[PHP Hammer] Type cast must be written as (int)">(integer)</weak_warning> $x;
$dummy = (float) $x;
$dummy = <weak_warning descr="[PHP Hammer] Type cast must be written as (float)">(double)</weak_warning> $x;
$dummy = <weak_warning descr="[PHP Hammer] Type cast must be written as (float)">(real)</weak_warning> $x;
$dummy = (bool) $x;
$dummy = <weak_warning descr="[PHP Hammer] Type cast must be written as (bool)">(BOOL)</weak_warning> $x;
$dummy = <weak_warning descr="[PHP Hammer] Type cast must be written as (bool)">(boolean)</weak_warning> $x;
$dummy = <weak_warning descr="[PHP Hammer] Type cast must be written as (bool)">( boolean )</weak_warning> $x;
