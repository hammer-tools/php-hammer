<?php

$a++;
$a--;

$a++;
$a--;

self::$a++;
self::$a++;

for (; ; $a++) {
}

for (; ; $a++) {
}

// Not applicable:

$dummy = ++$a;
$dummy = $a++;

$dummy = ++self::$a;
$dummy = self::$a++;
