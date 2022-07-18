<?php

$dummy = is_null($dummy);

$dummy = !is_null($dummy);

$dummy = <weak_warning descr="[PHP Hammer] Null check must be via is_null() function.">$dummy === null</weak_warning>;

$dummy = <weak_warning descr="[PHP Hammer] Null check must be via is_null() function.">$dummy !== null</weak_warning>;

$dummy = <weak_warning descr="[PHP Hammer] Null check must be via is_null() function.">null === $dummy</weak_warning>;

$dummy = <weak_warning descr="[PHP Hammer] Null check must be via is_null() function.">null !== $dummy</weak_warning>;

// Not applicable:

$dummy = $dummy == null;

$dummy = $dummy != null;
