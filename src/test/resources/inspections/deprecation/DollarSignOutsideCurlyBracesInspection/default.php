<?php

/** @var int $x */

$dummy = "<warning descr="[PHP Hammer] Deprecated: using ${var} in strings.">${x}</warning>";
$dummy = "<warning descr="[PHP Hammer] Deprecated: using ${var} in strings.">${$x}</warning>";
$dummy = "abc<warning descr="[PHP Hammer] Deprecated: using ${var} in strings.">${x}</warning>123";
$dummy = "abc<warning descr="[PHP Hammer] Deprecated: using ${var} in strings.">${$x}</warning>123";

// Not applicable:

$dummy = "{$x}";
$dummy = "{$$x}";
$dummy = '${x}';
