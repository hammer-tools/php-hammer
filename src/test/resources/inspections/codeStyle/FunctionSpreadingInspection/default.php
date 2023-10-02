<?php

$arr1 = [ 1, 2, 3 ];
$arr2 = [ 'a' => 1, 'b' => 2, 'c' => 3 ];
$arr3_Invalid = function (): array { return [ 1, 2, 3 ]; };
/** @return int[] */
$arr4_Invalid = function () { return [ 1, 2, 3 ]; };
$gen1 = function (): \Generator { yield 1; };
$gen2 = function (): \Generator { yield 'a' => 1; };
$gen3 = function (): \Generator {};
$gen4 = function () use($gen1): \Generator { yield from $gen1(); };
$gen5 = function () use($gen2): \Generator { yield from $gen2(); };
$gen6 = function () use($gen5): \Generator { yield from $gen5(); };
$fun1 = function (): int { return 123; };
$fun2 = function (): array { return [ 1, 2, 3 ]; };
/** @return int[] */
$fun3 = function () { return [ 1, 2, 3 ]; };

$dummy10400 = <weak_warning descr="🔨 PHP Hammer: function array_merge() can be replaced with spread.">array_merge($arr1, $arr2, $fun2())</weak_warning>;

$dummy10100 = <weak_warning descr="🔨 PHP Hammer: function array_merge() can be replaced with spread." textAttributesKey="INFO_ATTRIBUTES">array_merge($arr1, $arr2)</weak_warning>;

$dummy10110 = <weak_warning descr="🔨 PHP Hammer: function array_merge() can be replaced with spread." textAttributesKey="INFO_ATTRIBUTES">array_merge($arr1, $arr1)</weak_warning>;

$dummy10200 = <weak_warning descr="🔨 PHP Hammer: function array_merge() can be replaced with spread." textAttributesKey="INFO_ATTRIBUTES">array_merge($arr1, $arr2, [ 'x' => 1, 'y' => 2, 'z' => 3 ])</weak_warning>;

$dummy10400_Invalid = array_merge($arr1, $arr2, $arr3_Invalid);

$dummy10500 = <weak_warning descr="🔨 PHP Hammer: function array_merge() can be replaced with spread." textAttributesKey="INFO_ATTRIBUTES">array_merge($arr1, $arr2, $gen1())</weak_warning>;

$dummy10510 = <weak_warning descr="🔨 PHP Hammer: function array_merge() can be replaced with spread." textAttributesKey="INFO_ATTRIBUTES">array_merge($arr1, $arr2, $gen4())</weak_warning>;

$dummy10600 = <weak_warning descr="🔨 PHP Hammer: function array_merge() can be replaced with spread.">array_merge($arr1, $arr2, $fun2(), $fun3())</weak_warning>;

$dummy10600_Invalid = array_merge($arr1, $arr2, $arr3_Invalid, $arr4_Invalid);

$dummy10700 = <weak_warning descr="🔨 PHP Hammer: function array_merge() can be replaced with spread." textAttributesKey="INFO_ATTRIBUTES">array_merge([ 1 ], [ 2, 3 ])</weak_warning>;

$dummy10800 = <weak_warning descr="🔨 PHP Hammer: function array_merge() can be replaced with spread." textAttributesKey="INFO_ATTRIBUTES">array_merge([ 0 => 1 ], [ 1 => 2, 2 => 3 ])</weak_warning>;

$dummy10900 = <weak_warning descr="🔨 PHP Hammer: function array_merge() can be replaced with spread." textAttributesKey="INFO_ATTRIBUTES">array_merge([ 'a' => 1 ], [ 'b' => 2, 'c' => 3 ])</weak_warning>;

// Not applicable:

$dummy90300 = array_merge();

$dummy90400 = array_merge($arr1); // Not applicable because another inspection will ask for replacement to just $arr1.

$dummy90500 = array_merge($arr1, $arr2, $gen2());

$dummy90510 = array_merge($arr1, $arr2, $gen5());

$dummy90511 = array_merge($arr1, $arr2, $gen6());

$dummy90520 = array_merge($arr1, $arr2, $gen3());

$dummy90600 = array_merge($arr1, $arr2, $unknown());

$dummy90700 = array_merge($arr1, $arr2, unknown());

$dummy90800 = array_merge($arr1, $arr2, $fun1());

$dummy90900 = array_merge([], ...$arr2);

$dummy91000 = array_merge($arr1, $arr2, ...[ [ 'x' => 1, 'y' => 2, 'z' => 3 ] ]);
