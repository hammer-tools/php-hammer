<?php

$case_1000 = <warning descr="🔨 PHP Hammer: this function is obsolete, use random_int() instead.">rand</warning>(1, 5);
$case_1010 = \\<warning descr="🔨 PHP Hammer: this function is obsolete, use random_int() instead.">rand</warning>(1, 5);

$case_2000 = <warning descr="🔨 PHP Hammer: this function is obsolete, use random_int() instead.">mt_rand</warning>(1, 5);
$case_2010 = \\<warning descr="🔨 PHP Hammer: this function is obsolete, use random_int() instead.">mt_rand</warning>(1, 5);
