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

for (; ; $a++, $b++) {
}

for (; ; $a--, $b--) {
}

// Not applicable:

$dummy = ++$a;
$dummy = $a++;

$dummy = ++self::$a;
$dummy = self::$a++;
