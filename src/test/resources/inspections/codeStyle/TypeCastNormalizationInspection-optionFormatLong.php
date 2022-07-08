<?php

$dummy = <weak_warning descr="[PHP Hammer] Type cast must be written as (integer)">(int)</weak_warning> $x;
$dummy = (integer) $x;
$dummy = (float) $x;
$dummy = <weak_warning descr="[PHP Hammer] Type cast must be written as (float)">(double)</weak_warning> $x;
$dummy = <weak_warning descr="[PHP Hammer] Type cast must be written as (float)">(real)</weak_warning> $x;
$dummy = <weak_warning descr="[PHP Hammer] Type cast must be written as (boolean)">(bool)</weak_warning> $x;
$dummy = <weak_warning descr="[PHP Hammer] Type cast must be written as (boolean)">(BOOL)</weak_warning> $x;
$dummy = (boolean) $x;
$dummy = ( boolean ) $x;
