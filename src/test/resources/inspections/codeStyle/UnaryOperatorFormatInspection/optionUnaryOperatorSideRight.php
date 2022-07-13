<?php

<weak_warning descr="[PHP Hammer] Unary expression must be written as $a++">++$a</weak_warning>;
<weak_warning descr="[PHP Hammer] Unary expression must be written as $a--">--$a</weak_warning>;

$a++;
$a--;

self::$a++;
<weak_warning descr="[PHP Hammer] Unary expression must be written as self::$a++">++self::$a</weak_warning>;

for (; ; $a++) {
}

for (; ; <weak_warning descr="[PHP Hammer] Unary expression must be written as $a++">++$a</weak_warning>) {
}

// Not applicable:

$dummy = ++$a;
$dummy = $a++;

$dummy = ++self::$a;
$dummy = self::$a++;
