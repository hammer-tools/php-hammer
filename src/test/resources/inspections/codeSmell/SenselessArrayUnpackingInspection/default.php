<?php

$array       = [ 1, 2, 3 ];
$arrayObject = new ArrayObject($array);

$dummy = <weak_warning descr="🔨 PHP Hammer: unnecessary array unpacking.">[ ... $array ]</weak_warning>;
$dummy = <weak_warning descr="🔨 PHP Hammer: unnecessary array unpacking.">[ ... $arrayObject ]</weak_warning>;

$dummy1000function = function (): array { return []; };
$dummy1000 = <weak_warning descr="🔨 PHP Hammer: unnecessary array unpacking.">[ ... $dummy1000function() ]</weak_warning>;

$dummy2000function = function (): ArrayObject { return []; };
$dummy2000 = <weak_warning descr="🔨 PHP Hammer: unnecessary array unpacking.">[ ... $dummy2000function() ]</weak_warning>;

$dummy3000function = function (): array|ArrayObject { return []; };
$dummy3000 = <weak_warning descr="🔨 PHP Hammer: unnecessary array unpacking.">[ ... $dummy3000function() ]</weak_warning>;

/** @var int[]|ArrayObject */
$dummy4000function = function () { return []; };
$dummy4000 = <weak_warning descr="🔨 PHP Hammer: unnecessary array unpacking.">[ ... $dummy3000function() ]</weak_warning>;

// Not applicable:

$dummy = [ 1 ];
$dummy = [ ... [] ];

$dummy = [ 1, ... $array ];
$dummy = [ ... $array, ... $array ];

$object = new stdClass();

$dummy = [ ... $object ];

$unknown = unknown();

$dummy = [ ... $unknown ];

$dummy99000function = function (): array|ArrayObject|int { return []; };
$dummy99000 = [ ... $dummy() ];
