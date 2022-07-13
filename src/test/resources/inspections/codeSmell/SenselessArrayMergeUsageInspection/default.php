<?php

$dummy = <weak_warning descr="[PHP Hammer] Senseless array_merge() usage.">array_merge()</weak_warning>;

$dummy = <weak_warning descr="[PHP Hammer] Senseless array_merge() usage.">array_merge([1, 2, 3])</weak_warning>;

// Not applicable:

$dummy = array_merge([1, 2, 3], [1, 2, 3]);

$dummy = array_merge(... [1, 2, 3]);

$dummy = array_merge(... x());
