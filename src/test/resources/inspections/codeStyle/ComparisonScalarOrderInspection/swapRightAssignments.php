<?php

$dummy = <weak_warning descr="🔨 PHP Hammer: scalar type must be on the right side.">123 === $dummy = 123</weak_warning>;
$dummy = <weak_warning descr="🔨 PHP Hammer: scalar type must be on the right side.">123 === $dummy += 123</weak_warning>;
$dummy = <weak_warning descr="🔨 PHP Hammer: scalar type must be on the right side.">123 === $dummy ??= 123</weak_warning>;
