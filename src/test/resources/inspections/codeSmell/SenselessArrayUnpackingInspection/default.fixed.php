<?php

$array       = [ 1, 2, 3 ];
$arrayObject = new ArrayObject($array);

$dummy = $array;
$dummy = $arrayObject;

$dummy1000function = function (): array { return []; };
$dummy1000 = $dummy1000function();

$dummy2000function = function (): ArrayObject { return []; };
$dummy2000 = $dummy2000function();

$dummy3000function = function (): array|ArrayObject { return []; };
$dummy3000 = $dummy3000function();

/** @var int[]|ArrayObject */
$dummy4000function = function () { return []; };
$dummy4000 = $dummy4000function();

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

$dummy99100function = function (): Generator { yield 1; };
$dummy99100 = [ ... $dummy99100function() ];

function dummy99200function(): Generator { yield 1; }
$dummy99200 = [ ... dummy99200function() ];
