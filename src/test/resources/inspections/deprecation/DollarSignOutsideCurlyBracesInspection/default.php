<?php

/** @var int $x */

$dummy = "<warning descr="🔨 PHP Hammer: using ${var} in strings is deprecated.">${x}</warning>";
$dummy = "<warning descr="🔨 PHP Hammer: using ${var} in strings is deprecated.">${$x}</warning>";
$dummy = "abc<warning descr="🔨 PHP Hammer: using ${var} in strings is deprecated.">${x}</warning>123";
$dummy = "abc<warning descr="🔨 PHP Hammer: using ${var} in strings is deprecated.">${$x}</warning>123";

// Not applicable:

$dummy = "{$x}";
$dummy = "{$$x}";
$dummy = '${x}';
