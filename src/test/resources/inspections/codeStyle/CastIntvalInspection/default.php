<?php

/** @var object $z */

$dummy = <weak_warning descr="🔨 PHP Hammer: function cast can be replaced with the type cast (bool).">boolval($z)</weak_warning>;
$dummy = <weak_warning descr="🔨 PHP Hammer: function cast can be replaced with the type cast (int).">intval($z)</weak_warning>;
$dummy = <weak_warning descr="🔨 PHP Hammer: function cast can be replaced with the type cast (float).">floatval($z)</weak_warning>;
$dummy = <weak_warning descr="🔨 PHP Hammer: function cast can be replaced with the type cast (float).">doubleval($z)</weak_warning>;
$dummy = <weak_warning descr="🔨 PHP Hammer: function cast can be replaced with the type cast (string).">strval($z)</weak_warning>;
$dummy = <weak_warning descr="🔨 PHP Hammer: function cast can be replaced with the type cast (string).">STRVAL($z)</weak_warning>;

<weak_warning descr="🔨 PHP Hammer: function cast can be replaced with the type cast (bool).">settype($z, 'bool')</weak_warning>;
<weak_warning descr="🔨 PHP Hammer: function cast can be replaced with the type cast (bool).">settype($z, 'boolean')</weak_warning>;
<weak_warning descr="🔨 PHP Hammer: function cast can be replaced with the type cast (int).">settype($z, 'int')</weak_warning>;
<weak_warning descr="🔨 PHP Hammer: function cast can be replaced with the type cast (int).">settype($z, 'integer')</weak_warning>;
<weak_warning descr="🔨 PHP Hammer: function cast can be replaced with the type cast (float).">settype($z, 'float')</weak_warning>;
<weak_warning descr="🔨 PHP Hammer: function cast can be replaced with the type cast (float).">settype($z, 'double')</weak_warning>;
<weak_warning descr="🔨 PHP Hammer: function cast can be replaced with the type cast (string).">settype($z, 'string')</weak_warning>;
<weak_warning descr="🔨 PHP Hammer: function cast can be replaced with the type cast (string).">settype($z, 'STRING')</weak_warning>;
<weak_warning descr="🔨 PHP Hammer: function cast can be replaced with the type cast (array).">settype($z, 'array')</weak_warning>;
<weak_warning descr="🔨 PHP Hammer: function cast can be replaced with the type cast (object).">settype($z, 'object')</weak_warning>;
<weak_warning descr="🔨 PHP Hammer: function cast can be replaced with null.">settype($z, 'null')</weak_warning>;

$dummy = <weak_warning descr="🔨 PHP Hammer: function cast can be replaced with the type cast (int).">intval($z, 10)</weak_warning>;

/** @var object $z */

$dummy = <weak_warning descr="🔨 PHP Hammer: function cast can be replaced with the type cast (int).">intval($z::class)</weak_warning>;
$dummy = <weak_warning descr="🔨 PHP Hammer: function cast can be replaced with the type cast (int).">intval(is_object($z) + 1)</weak_warning>;
$dummy = <weak_warning descr="🔨 PHP Hammer: function cast can be replaced with the type cast (int).">intval($z)</weak_warning> . <weak_warning descr="🔨 PHP Hammer: function cast can be replaced with the type cast (int).">intval($z + 1)</weak_warning>;

// Not applicable:

$dummy = boolval();
$dummy = intval($z, 16);

$dummy = settype($z, 'int');

settype();
settype($z);
settype($z, $x);

// Error:

settype(<error descr="Expected: expression">,</error> "int");
settype(123, "int");
